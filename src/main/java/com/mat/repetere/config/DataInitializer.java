package com.mat.repetere.config;

import com.mat.repetere.model.*;
import com.mat.repetere.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final DeckRepository deckRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           LanguageRepository languageRepository,
                           DeckRepository deckRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.deckRepository = deckRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // cargar idiomas si noe xisten
        if (languageRepository.count() == 0) {
            Language english = new Language();
            english.setCode("en-US");
            english.setName("Inglés");
            english.setTtsVoice("en-US-AriaNeural");

            Language spanish = new Language();
            spanish.setCode("es-ES");
            spanish.setName("Español");
            spanish.setTtsVoice("es-ES-ElviraNeural");

            languageRepository.save(english);
            languageRepository.save(spanish);
            System.out.println("-> Idiomas de prueba cargados.");
        }

        if (userRepository.count() == 1){
            User user = new User();
            user.setName("user");
            user.setEmail("user@repetere.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setNativeLanguage(NativeLanguage.ES);
            user.setRole(Role.ADMIN);
            user.setActive(true);
            user.setForcePasswordChange(false);

            userRepository.save(user);
            System.out.println("-> Usuario user creado (user@repetere.com / user123).");
        }

        // cargar admin si no hay usuarios
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Admin Repetere");
            admin.setEmail("admin@repetere.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNativeLanguage(NativeLanguage.ES);
            admin.setRole(Role.USER);
            admin.setActive(true);
            admin.setForcePasswordChange(false);

            userRepository.save(admin);
            System.out.println("-> Usuario Admin creado (admin@repetere.com / admin123).");

            // cargar mazo de prueba
            Language langEn = languageRepository.findByCode("en-US").orElseThrow();
            Language langEs = languageRepository.findByCode("es-ES").orElseThrow();

            Deck testDeck = new Deck();
            testDeck.setUser(admin);
            testDeck.setName("Inglés - Frases Frecuentes");
            testDeck.setLangFrom(langEn);
            testDeck.setLangTo(langEs);

            deckRepository.save(testDeck);
            System.out.println("-> Mazo de prueba creado.");
        }
    }
}