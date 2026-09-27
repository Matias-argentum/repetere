package com.mat.repetere.service.deck;

import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.repository.DeckRepository;
import org.springframework.stereotype.Service;

@Service
public class DeckDeleter {

    private final DeckRepository repository;

    public DeckDeleter(DeckRepository repository) {
        this.repository = repository;
    }

    public void delete(Long deckId){
        repository.deleteById(deckId);
    }
}
