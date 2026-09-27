package com.mat.repetere.service.deck;

import com.mat.repetere.dto.deck.DeckRequestDto;
import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.dto.deck.ParsedCsvLineDto;
import com.mat.repetere.exception.LanguageNotFoundException;
import com.mat.repetere.exception.UserNotFoundException;
import com.mat.repetere.model.Deck;
import com.mat.repetere.model.DeckStatus;
import com.mat.repetere.model.Language;
import com.mat.repetere.model.User;
import com.mat.repetere.repository.CardRepository;
import com.mat.repetere.repository.DeckRepository;
import com.mat.repetere.repository.LanguageRepository;
import com.mat.repetere.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeckCreator {
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public DeckCreator(UserRepository userRepository, LanguageRepository languageRepository, CardRepository cardRepository, DeckRepository deckRepository) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    @Transactional
    public DeckResponseDto create(DeckRequestDto request, List<ParsedCsvLineDto> parsedRows, Long ownerId){
        User user = userRepository.findById(ownerId).orElseThrow(()->new UserNotFoundException(ownerId));
        //System.out.println("TARGETLANGIAGECODE: " + request.targetLanguageCode());
        Language targetLanguage = languageRepository.findById(request.targetLanguageId()).orElseThrow(()-> new LanguageNotFoundException("language not found"));

        Deck deck = new Deck();
        deck.setDeckStatus(DeckStatus.PROCESSING);
        deck.setCreatedAt(LocalDateTime.now());
        deck.setUser(user);
        deck.setName(request.deckName());
        deck.setLangTo(targetLanguage);

        Deck savedDeck = deckRepository.save(deck);
        cardRepository.saveAll(parsedRows.stream().map( line -> ParsedCsvLineDto.fromDtoToCardEntity(line, savedDeck)).toList());

        return new DeckResponseDto(
                savedDeck.getId(),
                savedDeck.getName(),
                savedDeck.getDeckStatus(),
                targetLanguage.getCode(),
                parsedRows.size(),
                savedDeck.getCreatedAt()
        );
    }
}
