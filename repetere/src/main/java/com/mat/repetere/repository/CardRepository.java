package com.mat.repetere.repository;

import com.mat.repetere.model.Card;
import com.mat.repetere.model.CardState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByDeckId(Long deckId);
    long countByDeckId(Long deckId);

    @Query("SELECT c FROM Card c WHERE c.deck.id = :deckId AND (c.cardState = :newState OR c.nextReviewAt <= :now)")
    List<Card> findDueCards(@Param("deckId") Long deckId, @Param("newState") CardState newState, @Param("now") LocalDateTime now);

    @Query("SELECT COUNT(c) FROM Card c JOIN c.deck d WHERE d.user.id = :userId AND (c.nextReviewAt IS NULL OR c.nextReviewAt <= :now)")
    Long countDueCards(@Param("now") LocalDateTime now, @Param("userId") Long userId);

    @Query("SELECT c.cardState, COUNT(c) FROM Card c JOIN c.deck d WHERE d.user.id = :userId GROUP BY c.cardState")
    List<Object[]> countCardsByState(@Param("userId") Long userId);

}
