package com.mat.repetere.service.prompt;

import com.mat.repetere.dto.prompt.GeneratePromptRequestDto;
import com.mat.repetere.exception.LanguageNotFoundException;
import com.mat.repetere.exception.PromptNotFpundException;
import com.mat.repetere.model.Language;
import com.mat.repetere.model.NativeLanguage;
import com.mat.repetere.repository.LanguageRepository;
import com.mat.repetere.repository.PromptTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PromptGenerator {
    private final PromptTemplateRepository promptTemplateRepository;
    private final LanguageRepository languageRepository;

    public PromptGenerator(PromptTemplateRepository repository, LanguageRepository languageRepository) {
        this.promptTemplateRepository = repository;
        this.languageRepository = languageRepository;
    }

    public String generatePrompt(GeneratePromptRequestDto request, NativeLanguage nativeLanguage){

        String template = promptTemplateRepository.findByNativeLanguageAndActive(nativeLanguage, true).orElseThrow(()-> new PromptNotFpundException("Prompt not found")).getText();
        Language targetLanguage = languageRepository.findById(request.targetLanguageId()).orElseThrow(()-> new LanguageNotFoundException("Language not found"));
        template = template.replace("{targetLanguage}", targetLanguage.getName());
        template = template.replace("{nativeLanguage}", nativeLanguage.name());
        template = template.replace("{level}", request.level());
        template = template.replace("{topic}", request.topic());
        if (!Objects.equals(request.clarifications(), "")){
            template = template.replace("{clarifications}", request.clarifications());
        }else{
            template = template.replace("Ten en cuenta además las siguientes aclaraciones: {clarifications}", "");
        }

        return template;
    }
}
