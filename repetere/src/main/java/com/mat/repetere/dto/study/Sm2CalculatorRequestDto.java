package com.mat.repetere.dto.study;

public record Sm2CalculatorRequestDto(
        double easeFactor,
        int repetitions,
        int intervalDays,
        int rating
) {
}
