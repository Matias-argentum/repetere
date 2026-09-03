/*
package com.mat.repetere.config;

import com.repetere.model.Language;
import com.repetere.model.User;
import com.repetere.repository.LanguageRepository;
import com.repetere.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            LanguageRepository languageRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Cargar idiomas si la tabla está vacía
            if (languageRepository.count() == 0) {
                List<Language> languages = List.of(
                        new Language("en-US", "Inglés", "en-US-AriaNeural"),
                        new Language("es-ES", "Español", "es-ES-ElviraNeural"),
                        new Language("zh-CN", "Chino Mandarín", "zh-CN-XiaoxiaoNeural"),
                        new Language("ja-JP", "Japonés", "ja-JP-NanamiNeural"),
                        new Language("fr-FR", "Francés", "fr-FR-DeniseNeural"),
                        new Language("de-DE", "Alemán", "de-DE-KatjaNeural"),
                        new Language("pt-BR", "Portugués (Brasil)", "pt-BR-FranciscaNeural"),
                        new Language("it-IT", "Italiano", "it-IT-ElsaNeural")
                );
                languageRepository.saveAll(languages);
            }

            // 2. Crear usuario Admin inicial si no existe
            if (!userRepository.existsByEmail("admin@repetere.com")) {
                User admin = new User();
                admin.setName("Admin Repetere");
                admin.setEmail("admin@repetere.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña hasheada
                admin.setNativeLanguage("ES");
                admin.setAdmin(true);
                admin.setCreatedAt(LocalDateTime.now());

                userRepository.save(admin);
            }
        };
    }
* */