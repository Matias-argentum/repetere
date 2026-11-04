package com.mat.repetere.service.stats;

import com.mat.repetere.dto.stats.UserStatsResponseDto;
import com.mat.repetere.model.CardState;
import com.mat.repetere.repository.CardRepository;
import com.mat.repetere.repository.ReviewLogRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StatsSearcherService {
    private final Clock clock;
    private final CardRepository cardRepository;
    private final ReviewLogRepository reviewLogRepository;

    public StatsSearcherService(Clock clock, CardRepository cardRepository, ReviewLogRepository reviewLogRepository) {
        this.clock = clock;
        this.cardRepository = cardRepository;
        this.reviewLogRepository = reviewLogRepository;
    }

    public UserStatsResponseDto getUserStats(Long userId){

        Long dueCards = cardRepository.countDueCards(LocalDateTime.now(clock), userId);
        Long cardsReviewedToday = reviewLogRepository.countTotalReviewsSince(userId, LocalDateTime.now(clock).withHour(0).withMinute(0));
        int currentStreak = obtainStreak(userId);
        Long totalReviewsLast30Days = reviewLogRepository.countTotalReviewsSince(userId, LocalDateTime.now(clock).minusDays(30));
        String retentionRate = findRetentionRate(userId, LocalDateTime.now(clock).minusDays(30), totalReviewsLast30Days);
        Map<CardState, Long> cardsByState = findCardsByState(userId);

        return new UserStatsResponseDto(
                cardsReviewedToday,
                dueCards,
                cardsByState,
                currentStreak,
                retentionRate,
                totalReviewsLast30Days
        );
    }

    private int obtainStreak(Long userId) {

        LocalDate today = LocalDate.now(clock);
        boolean countFromToday = false;
        int streak = 0;

        List<LocalDateTime> allDateTimes = reviewLogRepository.findReviewDatesByUserId(userId);
        Set<LocalDate> allLocalDates = new HashSet<>();
        for (LocalDateTime ld : allDateTimes) {
            allLocalDates.add(ld.toLocalDate());
        }
        if (allLocalDates.contains(today)){
            countFromToday = true;
        } else if (allLocalDates.contains(today.minusDays(1))) {
            countFromToday = false;
        } else {
            return 0;
        }

        LocalDate lastDayWithReviews = countFromToday ? today : today.minusDays(1);

        while(allLocalDates.contains(lastDayWithReviews)){
            streak ++;
            lastDayWithReviews = lastDayWithReviews.minusDays(1);
        }
        return streak;
    }

    private String findRetentionRate (Long userId, LocalDateTime since, Long totalReviewsLast30Days){

        //Long totalReviews = reviewLogRepository.countTotalReviewsSince(userId, since);
        Long totalReviews = totalReviewsLast30Days; // esto es solo para ahorrarme una consulta
        Long successfulReviews = reviewLogRepository.countSuccessfulReviewsSince(userId, since);


        return totalReviews == 0 ? "-" : String.format("%.1f%%", (double) successfulReviews / totalReviews * 100);
    }

    private Map<CardState, Long> findCardsByState(Long userId){
        Map<CardState, Long> cardsByState = new HashMap<>();

        // inicializa todos los estados en 0
        for (CardState state : CardState.values()) {
            cardsByState.put(state, 0L);
        }

        // pisa con los valores reales que devuelve la BD
        cardRepository.countCardsByState(userId).forEach(o -> {
            cardsByState.put((CardState) o[0], (Long) o[1]);
        });

        return cardsByState;
    }


}
