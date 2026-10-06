package com.mat.repetere.service.study;

import com.mat.repetere.dto.study.Sm2CalculatorRequestDto;
import com.mat.repetere.dto.study.Sm2CalculatorResponseDto;
import com.mat.repetere.dto.study.StudyCardDto;
import com.mat.repetere.model.Card;
import com.mat.repetere.model.CardState;
import com.mat.repetere.model.ReviewLog;
import com.mat.repetere.repository.CardRepository;
import com.mat.repetere.repository.ReviewLogRepository;
import com.mat.repetere.service.card.CardFinder;
import com.mat.repetere.service.card.CardSearcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CardReviewProcessor {

    private final CardFinder cardFinder;
    private final Sm2Calculator sm2Calculator;
    private final CardRepository cardRepository;
    private final ReviewLogRepository reviewLogRepository;
    private final CardSearcher cardSearcher;

    public CardReviewProcessor(CardFinder cardFinder, Sm2Calculator sm2Calculator, CardRepository cardRepository, ReviewLogRepository reviewLogRepository, CardSearcher cardSearcher) {
        this.cardFinder = cardFinder;
        this.sm2Calculator = sm2Calculator;
        this.cardRepository = cardRepository;
        this.reviewLogRepository = reviewLogRepository;
        this.cardSearcher = cardSearcher;
    }

    public StudyCardDto submitRating(Long cardId, Long deckId, int rating) {
        Card card = cardFinder.findById(cardId);
        Sm2CalculatorResponseDto result = sm2Calculator.calculate(
                new Sm2CalculatorRequestDto(card.getEaseFactor(), card.getRepetitions(), card.getIntervalDays(), rating)
        );

        card.setEaseFactor(result.newEaseFactor());
        card.setRepetitions(result.newRepetitions());
        card.setIntervalDays(result.newIntervalDays());
        card.setNextReviewAt(LocalDateTime.now().plusDays(result.newIntervalDays()));
        if (result.newIntervalDays() >= 7) {
            card.setCardState(CardState.REVIEW);
        } else {
            card.setCardState(CardState.LEARNING);
        }
        cardRepository.save(card);
        ReviewLog reviewLog = new ReviewLog();
        reviewLog.setCard(card);
        reviewLog.setRatingChosen(rating);
        reviewLog.setReviewedAt(LocalDateTime.now());
        reviewLogRepository.save(reviewLog);

        return cardSearcher.findNextDueCard(deckId).orElse(null);
    }
}
