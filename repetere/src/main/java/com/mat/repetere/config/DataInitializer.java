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
    private final  PromptTemplateRepository promptTemplateRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           LanguageRepository languageRepository,
                           DeckRepository deckRepository, PromptTemplateRepository promptTemplateRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.deckRepository = deckRepository;
        this.promptTemplateRepository = promptTemplateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // cargar idiomas si noe xisten
        if (languageRepository.count() == 0) {
            Language english = new Language();
            english.setCode("en-US");
            english.setName("English (US)");
            english.setTtsVoice("en-US-AriaNeural");
            languageRepository.save(english);

            Language spanishAr = new Language();
            spanishAr.setCode("es-AR");
            spanishAr.setName("Español (Argentina)");
            spanishAr.setTtsVoice("es-AR-ElenaNeural");
            languageRepository.save(spanishAr);

            Language spanishMx = new Language();
            spanishMx.setCode("es-MX");
            spanishMx.setName("Español (México)");
            spanishMx.setTtsVoice("es-MX-DaliaNeural");
            languageRepository.save(spanishMx);

            Language portugueseBr = new Language();
            portugueseBr.setCode("pt-BR");
            portugueseBr.setName("Português (Brasil)");
            portugueseBr.setTtsVoice("pt-BR-FranciscaNeural");
            languageRepository.save(portugueseBr);

            Language french = new Language();
            french.setCode("fr-FR");
            french.setName("Français");
            french.setTtsVoice("fr-FR-DeniseNeural");
            languageRepository.save(french);

            Language german = new Language();
            german.setCode("de-DE");
            german.setName("Deutsch");
            german.setTtsVoice("de-DE-KatjaNeural");
            languageRepository.save(german);

            Language italian = new Language();
            italian.setCode("it-IT");
            italian.setName("Italiano");
            italian.setTtsVoice("it-IT-ElsaNeural");
            languageRepository.save(italian);

            Language chinese = new Language();
            chinese.setCode("zh-CN");
            chinese.setName("中文 (普通话)");
            chinese.setTtsVoice("zh-CN-XiaoxiaoNeural");
            languageRepository.save(chinese);
            System.out.println("-> Idiomas de prueba cargados.");
        }

        if (userRepository.count() == 1){
            User user = new User();
            user.setName("user");
            user.setEmail("user@repetere.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setNativeLanguage(NativeLanguage.ES);
            user.setRole(Role.USER);
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
            admin.setRole(Role.ADMIN);
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
            testDeck.setLangTo(langEs);

            deckRepository.save(testDeck);
            System.out.println("-> Mazo de prueba creado.");
        }

        if (promptTemplateRepository.count() == 0){
            PromptTemplate template = new PromptTemplate();
            template.setActive(true);
            template.setNativeLanguage(NativeLanguage.ES);
            template.setText("""
                    Hola, quiero aprender el idioma: {targetLanguage}. Mi idioma nativo es: {nativeLanguage}. Mi nivel actual es: {level}.
                    
                    Necesito un archivo en formato CSV donde cada línea represente una tarjeta para aprender mediante repetición espaciada, sobre el tema: {topic}.
                    
                    Cada línea debe tener exactamente estos 5 campos, separados por punto y coma (;), en este orden exacto:
                    palabra_en_idioma_objetivo;pronunciacion_fonetica;traduccion_a_mi_idioma_nativo;frase_de_ejemplo_en_idioma_objetivo;traduccion_de_la_frase_a_mi_idioma_nativo
                    
                    Reglas estrictas:
                    - Máximo 25 líneas.
                            - Las frases de ejemplo no pueden superar los 140 caracteres cada una.
                            - No agregues encabezado ni títulos, solo las líneas de datos.
                    - No agregues líneas en blanco entre los datos.
                            - No agregues espacios antes o después de cada punto y coma.
                    - No uses comillas ni caracteres especiales que no sean los normales del idioma.
                    - Cada línea termina con un salto de línea simple, sin líneas vacías extra al final.
                    - El vocabulario y la complejidad de las frases deben ser apropiados para el nivel {level} según el Marco Común Europeo de Referencia (MCER/CEFR).
                    - No incluyas ningún carácter invisible, marca de codificación (BOM), ni metadatos al inicio del archivo. La primera línea debe comenzar directamente con el primer campo de datos.
                            Ejemplo de una línea válida:
                    hello;/heˈloʊ/;hola;hello my name is Juan;hola mi nombre es Juan
                    
                    Ten en cuenta además las siguientes aclaraciones: {clarifications}
                    
            """);

            promptTemplateRepository.save(template);
        }
    }
}