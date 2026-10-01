package com.mat.repetere.dto.deck;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TtsGenerationResponseDto(
        Long deckId,
        String minioLangFolderPath,
        String voice,
        List<TtsCardGenerationResponseDto> cards
) {
}

