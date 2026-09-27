package com.mat.repetere.dto.deck;

import com.mat.repetere.model.Deck;
import com.mat.repetere.model.DeckStatus;

import java.time.LocalDateTime;
import java.util.List;

public record DeckResponseDto(
        Long id,
        Long userId,
        String name,
        DeckStatus status,
        String targetLanguageCode,
        int cardCount,
        LocalDateTime createdAt
) {
    public static DeckResponseDto fromEntity(Deck deck){
        return new DeckResponseDto(deck.getId(), deck.getUser().getId(), deck.getName(), deck.getDeckStatus(), deck.getLangTo().getCode(), 25, deck.getCreatedAt());
    }
}
