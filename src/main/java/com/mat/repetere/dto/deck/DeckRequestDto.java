package com.mat.repetere.dto.deck;

public record DeckRequestDto(
        Long targetLanguageId,
        String deckName
) {
}
