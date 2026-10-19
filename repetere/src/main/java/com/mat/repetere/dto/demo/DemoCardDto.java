package com.mat.repetere.dto.demo;

public record DemoCardDto(
        String wordTarget,
        String phonetic,
        String wordTranslation,
        String sentenceTarget,
        String sentenceTranslation,
        String wordTargetAudioUrl,
        String sentenceTargetAudioUrl
) {
}
