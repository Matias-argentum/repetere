package com.mat.repetere.service.deck;

import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.model.Deck;
import com.mat.repetere.repository.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckFinder {
    private final DeckRepository repository;

    public DeckFinder(DeckRepository repository) {
        this.repository = repository;
    }

    public DeckResponseDto findById(Long deckId){
        Deck foundDeck = repository.findById(deckId).orElseThrow(()-> new RuntimeException("Deck not found"));
        return DeckResponseDto.fromEntity(foundDeck);
    }
}
