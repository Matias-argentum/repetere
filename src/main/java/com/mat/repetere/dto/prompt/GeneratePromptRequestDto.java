package com.mat.repetere.dto.prompt;

public record GeneratePromptRequestDto(
        Long targetLanguageId,
        String level,
        String topic,
        String clarifications
) {
}
