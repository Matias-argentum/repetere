package com.mat.repetere.service.card;

import com.mat.repetere.dto.card.CardResponseDto;
import com.mat.repetere.repository.CardRepository;
import com.mat.repetere.service.file.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
