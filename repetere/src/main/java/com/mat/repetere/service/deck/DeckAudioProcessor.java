package com.mat.repetere.service.deck;

import com.mat.repetere.dto.deck.TtsCardGenerationRequestDto;
import com.mat.repetere.dto.deck.TtsCardGenerationResponseDto;
import com.mat.repetere.dto.deck.TtsGenerationRequestDto;
import com.mat.repetere.dto.deck.TtsGenerationResponseDto;
import com.mat.repetere.model.Card;
import com.mat.repetere.model.Deck;
import com.mat.repetere.model.DeckStatus;
import com.mat.repetere.repository.CardRepository;
import com.mat.repetere.repository.DeckRepository;
import com.mat.repetere.service.file.FileService;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;

@Service
public class DeckAudioProcessor {

    private final DeckRepository deckRepository;
    private final RestClient ttsRestClient;
    private final CardRepository cardRepository;


    public DeckAudioProcessor(DeckRepository deckRepository, RestClient ttsRestClient, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.ttsRestClient = ttsRestClient;
        this.cardRepository = cardRepository;
    }

    @Async
    @Transactional
    public void generateAudios(Long deckId) {
        System.out.println("entramos al async CON EL DECK ID RECIBIDO DESDE EL CREATOR: " + deckId);
        Deck deck = deckRepository.findById(deckId).orElseThrow( () -> new RuntimeException("Deck not found"));
        String langCode = deck.getLangTo().getCode();
        String folderPath = langCode;
        String voice = deck.getLangTo().getTtsVoice();
        List<Card> cardList = deck.getCards();
        cardList.sort(Comparator.comparing(Card::getId));

        List <TtsCardGenerationRequestDto> ttsCardsRequest = cardList.stream()
                .map( c -> TtsCardGenerationRequestDto.fromCardToTtsRequest(
                        c, generateSha256Hash(langCode,
                                c.getWordTarget()),
                        generateSha256Hash(langCode, c.getSentenceTarget())))
                .toList();


        TtsGenerationRequestDto generationRequest = new TtsGenerationRequestDto(deck.getId(), folderPath, voice, ttsCardsRequest);

        //System.out.println("request ----> " + generationRequest);
        try {
            TtsGenerationResponseDto response = ttsRestClient.post()
                    .uri("/audio/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(generationRequest)
                    .retrieve()
                    .body(TtsGenerationResponseDto.class);

            if (response != null) {
                List<TtsCardGenerationResponseDto> cardsInResponse = response.cards();
                cardsInResponse.sort(Comparator.comparing(TtsCardGenerationResponseDto::id));

                for (int i = 0; i < cardsInResponse.size(); i++){
                    cardList.get(i).setSentenceAudioHash(cardsInResponse.get(i).sentenceAudioKey());
                    cardList.get(i).setWordAudioHash(cardsInResponse.get(i).wordAudioKey());
                }

                cardRepository.saveAll(cardList);
                deck.setDeckStatus(DeckStatus.READY);
                deckRepository.save(deck);

            }else{
                deck.setDeckStatus(DeckStatus.FAILED);
                deckRepository.save(deck);
            }
        } catch (HttpClientErrorException e) {
            deck.setDeckStatus(DeckStatus.FAILED);
            deckRepository.save(deck);
            System.out.println(e.getResponseBodyAsString());
        }
    }

    private String generateSha256Hash(String languageCode, String text) {

        String input = languageCode + "-" + text.toLowerCase();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(encodedHash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not found", e);
        }

    }
}
