package com.mat.repetere.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "decks")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_from_id", nullable = false)
    private Language langFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_to_id", nullable = false)
    private Language langTo;

    @Column(name = "csv_minio_key")
    private String csvMinioKey;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}