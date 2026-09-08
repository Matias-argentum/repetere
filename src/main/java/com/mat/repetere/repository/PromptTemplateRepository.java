package com.mat.repetere.repository;

import com.mat.repetere.model.PromptTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromptTemplateRepository extends JpaRepository<PromptTemplate, Long> {
    List<PromptTemplate> findByLanguageId(Long languageId);
    Optional<PromptTemplate> findByLanguageIdAndActiveTrue(Long languageId);
}