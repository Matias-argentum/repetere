package com.mat.repetere.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "rating_chosen", nullable = false)
    private int ratingChosen;

    @Column(name = "reviewed_at", nullable = false, updatable = false)
    private LocalDateTime reviewedAt = LocalDateTime.now();

}
