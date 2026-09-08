package com.mat.repetere.repository;

import com.mat.repetere.model.Deck;
import com.mat.repetere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findByUserId(Long userId);
    long countByUserId(Long userId);
}
