package com.mat.repetere.service.study;

import com.mat.repetere.dto.study.Sm2CalculatorRequestDto;
import com.mat.repetere.dto.study.Sm2CalculatorResponseDto;
import org.springframework.stereotype.Service;

@Service
public class Sm2Calculator {

    public Sm2CalculatorResponseDto calculate(Sm2CalculatorRequestDto request) {
        double easeFactor = request.easeFactor();
        int repetitions = request.repetitions();
        int rating = request.rating();

        double newEaseFactor = easeFactor + (0.1 - (5 - rating) * (0.08 + (5 - rating) * 0.02));
        if (newEaseFactor < 1.3) {
            newEaseFactor = 1.3;
        }

        if (rating < 3) {
            return new Sm2CalculatorResponseDto(newEaseFactor, 0, 1);
        }

        int newIntervalDays;
        if (repetitions == 0) {
            newIntervalDays = 1;
        } else if (repetitions == 1) {
            newIntervalDays = 6;
        } else {
            newIntervalDays = (int) Math.round(request.intervalDays() * newEaseFactor);
        }

        int newRepetitions = repetitions + 1;

        return new Sm2CalculatorResponseDto(newEaseFactor, newRepetitions, newIntervalDays);
    }
}
