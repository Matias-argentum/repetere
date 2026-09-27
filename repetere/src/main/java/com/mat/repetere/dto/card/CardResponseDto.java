package com.mat.repetere.dto.card;

import com.mat.repetere.model.Card;

public record CardResponseDto(
        Long id,
        String wordTarget,
        String phonetic,
        String sentenceTarget,
        String wordTargetAudioUrl,
        String sentenceTargetAudioUrl
) {
    public static CardResponseDto fromEntity(Card card, String wordAudio, String sentenceAudio){
        return new CardResponseDto(card.getId(), card.getWordTarget(), card.getPhonetic(), card.getSentenceTarget(), wordAudio, sentenceAudio);
    }
}
