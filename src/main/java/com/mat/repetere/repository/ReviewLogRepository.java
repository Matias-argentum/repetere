package com.mat.repetere.repository;

import com.mat.repetere.model.ReviewLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewLogRepository extends JpaRepository<ReviewLog, Long> {
    List<ReviewLog> findByCardIdOrderByReviewedAtDesc(Long cardId);
}