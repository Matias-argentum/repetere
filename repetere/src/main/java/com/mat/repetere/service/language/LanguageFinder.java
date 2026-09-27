package com.mat.repetere.service.language;

import com.mat.repetere.model.Language;
import com.mat.repetere.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageFinder {
    private final LanguageRepository repository;

    public LanguageFinder(LanguageRepository repository) {
        this.repository = repository;
    }

    public List<Language> findAll(){
        return repository.findAll();
    }
}
