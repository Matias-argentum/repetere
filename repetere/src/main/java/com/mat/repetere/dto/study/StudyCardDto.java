package com.mat.repetere.dto.study;

import com.mat.repetere.dto.card.CardResponseDto;
import com.mat.repetere.model.Card;

public record StudyCardDto(
        Long id,
        String wordTarget,
        String phonetic,
        String wordTargetAudioUrl,
        String wordTranslation,
        String sentenceTarget,
        String sentenceTargetAudioUrl,
        String sentenceTranslation
){
    public static StudyCardDto fromCardEntity(Card card, String wordAudio, String sentenceAudio){
        return new StudyCardDto(card.getId(),
                card.getWordTarget(),
                card.getPhonetic(),
                wordAudio,
                card.getWordTranslation(),
                card.getSentenceTarget(),
                sentenceAudio,
                card.getSentenceTranslation());
    }
}