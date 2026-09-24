package com.mat.repetere.service.deck;

import com.mat.repetere.model.Card;
import com.mat.repetere.model.Deck;
import com.mat.repetere.model.DeckStatus;
import com.mat.repetere.repository.CardRepository;
import com.mat.repetere.repository.DeckRepository;
import com.mat.repetere.service.file.FileService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckAudioProcessor {

    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;


    public DeckAudioProcessor(DeckRepository deckRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    @Async
    @Transactional
    public void generateAudios(Long deckId) {
        System.out.println("entramos al async CON EL DECK ID RECIBIDO DESDE EL CREATOR: " + deckId);
        Deck deck = deckRepository.findById(deckId).orElseThrow( () -> new RuntimeException("Deck not found"));
        List<Card> cardList = deck.getCards();

        try {
            for (Card card : cardList) {
                card.setWordAudioHash("en-US/hello.mp3");
                card.setSentenceAudioHash("en-US/hello.mp3");
            }
            Thread.sleep(10000);
            deck.setDeckStatus(DeckStatus.READY);
            cardRepository.saveAll(cardList);
            deckRepository.save(deck);
        } catch (InterruptedException e) {
            deck.setDeckStatus(DeckStatus.FAILED);
            deckRepository.save(deck);
            throw new RuntimeException(e);
        }finally {
            System.out.println("salimos del async");
        }


    }
}
