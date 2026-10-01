package com.mat.repetere.dto.deck;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TtsCardGenerationResponseDto(
        Long id,
        String wordAudioKey,
        String sentenceAudioKey,
        boolean wordReused,
        boolean sentenceReused
){
}
