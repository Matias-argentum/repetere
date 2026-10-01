package com.mat.repetere.dto.deck;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mat.repetere.model.Card;

import java.util.List;

public record TtsGenerationRequestDto(
        @JsonProperty("deck_id") Long deckId,
        @JsonProperty("minio_lang_folder_path") String minioLangFolderPath,
        String voice,
        List<TtsCardGenerationRequestDto> cards
) {
}

;