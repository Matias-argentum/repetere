package com.mat.repetere.dto.deck;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mat.repetere.model.Card;

public record TtsCardGenerationRequestDto(
        Long id,
        String word,
        @JsonProperty("word_hash") String wordHash,
        String sentence,
        @JsonProperty("sentence_hash") String sentenceHash
){
    public static  TtsCardGenerationRequestDto fromCardToTtsRequest(Card card, String wordHash, String sentenceHash){
        return new TtsCardGenerationRequestDto(card.getId(), card.getWordTarget(), wordHash, card.getSentenceTarget(), sentenceHash);
    }
}
