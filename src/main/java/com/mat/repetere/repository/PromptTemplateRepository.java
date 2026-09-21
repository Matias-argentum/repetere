package com.mat.repetere.repository;

import com.mat.repetere.model.NativeLanguage;
import com.mat.repetere.model.PromptTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromptTemplateRepository extends JpaRepository<PromptTemplate, Long> {
    Optional<PromptTemplate> findByNativeLanguageAndActive(NativeLanguage nativeLanguage, boolean active);
}