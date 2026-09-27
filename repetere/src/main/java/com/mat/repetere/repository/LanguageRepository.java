package com.mat.repetere.repository;

import com.mat.repetere.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByCode(String code);
}
