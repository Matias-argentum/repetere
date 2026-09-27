package com.mat.repetere.service.card;

import com.mat.repetere.dto.card.CardResponseDto;
import com.mat.repetere.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardSearcher {
    private final CardRepository repository;

    public CardSearcher(CardRepository repository) {
        this.repository = repository;
    }

    public List<CardResponseDto> findAllByDeckId(Long deckId){
        return repository.findByDeckId(deckId).stream().map(card -> CardResponseDto.fromEntity(card, "link", "link")).toList();
    }
}
