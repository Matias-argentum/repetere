package com.mat.repetere.repository;

import com.mat.repetere.model.ReviewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewLogRepository extends JpaRepository<ReviewLog, Long> {
    List<ReviewLog> findByCardIdOrderByReviewedAtDesc(Long cardId);

    @Query("SELECT COUNT(r) FROM ReviewLog r WHERE r.reviewedAt >= :start AND r.reviewedAt <= :end")
    Long countCardsToReviewInPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT r.reviewedAt FROM ReviewLog r JOIN r.card c JOIN c.deck d WHERE d.user.id = :userId ORDER BY r.reviewedAt DESC")
    List<LocalDateTime> findReviewDatesByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM ReviewLog r JOIN r.card c JOIN c.deck d WHERE d.user.id = :userId AND r.reviewedAt >= :since")
    Long countTotalReviewsSince(@Param("userId") Long userId, @Param("since") LocalDateTime since);

    @Query("SELECT COUNT(r) FROM ReviewLog r JOIN r.card c JOIN c.deck d WHERE d.user.id = :userId AND r.reviewedAt >= :since AND r.ratingChosen >= 3")
    Long countSuccessfulReviewsSince(@Param("userId") Long userId, @Param("since") LocalDateTime since); //mayor a 3 de rating
}