package com.mat.repetere.dto.deck;

import com.mat.repetere.model.Card;
import com.mat.repetere.model.CardState;
import com.mat.repetere.model.Deck;

import java.time.LocalDateTime;

public record ParsedCsvLineDto(
        String wordTarget,
        String phonetic,
        String wordTranslation,
        String sentenceTarget,
        String sentenceTranslation
) {
    public static Card fromDtoToCardEntity(ParsedCsvLineDto line, Deck deck){
        Card card = new Card();
        card.setDeck(deck);
        card.setCardState(CardState.NEW);
        card.setCreatedAt(LocalDateTime.now());
        card.setPhonetic(line.phonetic);
        card.setWordTarget(line.wordTarget);
        card.setWordTranslation(line.wordTranslation);
        card.setSentenceTranslation(line.sentenceTranslation);
        card.setSentenceTarget(line.sentenceTarget);

        return card;
    }
}
