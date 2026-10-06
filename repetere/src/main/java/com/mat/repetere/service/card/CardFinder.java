package com.mat.repetere.service.card;

import com.mat.repetere.model.Card;
import com.mat.repetere.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardFinder {
    private final CardRepository repository;

    public CardFinder(CardRepository repository) {
        this.repository = repository;
    }

    public Card findById(Long cardId){
        return repository.findById(cardId).orElseThrow(() -> new RuntimeException("CArd not found"));
    }
}
