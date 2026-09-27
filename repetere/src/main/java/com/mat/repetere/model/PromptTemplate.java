package com.mat.repetere.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prompt_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromptTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "native_language", nullable = false)
    private NativeLanguage nativeLanguage;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}
