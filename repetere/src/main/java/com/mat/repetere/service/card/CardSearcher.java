package com.mat.repetere.service.card;

import com.mat.repetere.dto.card.CardResponseDto;
import com.mat.repetere.dto.study.StudyCardDto;
import com.mat.repetere.model.Card;
import com.mat.repetere.model.CardState;
import com.mat.repetere.repository.CardRepository;
import com.mat.repetere.service.file.FileService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CardSearcher {
    private final CardRepository repository;
    private final FileService fileService;

    public CardSearcher(CardRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    public List<CardResponseDto> findAllByDeckId(Long deckId){
        return repository.findByDeckId(deckId)
                .stream()
                .map(card -> {
                    try {
                        return CardResponseDto.fromEntity(card,  fileService.getPresignedUrl(card.getWordAudioHash()), fileService.getPresignedUrl(card.getSentenceAudioHash()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }

    public Optional<StudyCardDto> findNextDueCard(Long deckId){
        List<Card> dueCards = repository.findDueCards(deckId, CardState.NEW, LocalDateTime.now());
        if (dueCards.isEmpty()){
            return Optional.empty();
        }
        Card nextDueCard = dueCards.getFirst();

        try {
            StudyCardDto nextCard = StudyCardDto.fromCardEntity(nextDueCard,
                    fileService.getPresignedUrl(nextDueCard.getWordAudioHash()),
                    fileService.getPresignedUrl(nextDueCard.getSentenceAudioHash())
                    );
            return Optional.of(nextCard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
