package com.mat.repetere.repository;

import com.mat.repetere.model.Card;
import com.mat.repetere.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByDeckId(Long deckId);
    long countByDeckId(Long deckId);
}
