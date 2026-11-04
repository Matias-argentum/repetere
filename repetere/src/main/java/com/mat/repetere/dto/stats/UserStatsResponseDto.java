package com.mat.repetere.dto.stats;

import com.mat.repetere.model.CardState;

import java.util.Map;

public record UserStatsResponseDto(
        Long reviewsToday,
        Long dueCardsCount,
        Map<CardState, Long> cardsByState,
        int currentStreak,
        String retentionRate,
        Long totalReviewsLast30Days
) {
}
