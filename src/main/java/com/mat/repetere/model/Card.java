package com.mat.repetere.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @Column(name = "word_target", nullable = false)
    private String wordTarget;

    private String phonetic;

    @Column(name = "word_translation", nullable = false)
    private String wordTranslation;

    @Column(name = "sentence_target", length = 140)
    private String sentenceTarget;

    @Column(name = "sentence_translation", length = 140)
    private String sentenceTranslation;

    @Column(name = "word_audio_hash")
    private String wordAudioHash;

    @Column(name = "sentence_audio_hash")
    private String sentenceAudioHash;

    // para el algoritmo SM-2
    @Column(name = "interval_days")
    private int intervalDays = 0;

    @Column(name = "ease_factor")
    private double easeFactor = 2.5;

    private int repetitions = 0;

    @Column(name = "next_review_at")
    private LocalDateTime nextReviewAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // en Card
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewLog> reviewLogs;

    @Column(name = "card_state")
    private CardState cardState;


}
