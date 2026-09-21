package com.mat.repetere.controller.prompt;

import com.mat.repetere.dto.prompt.GeneratePromptRequestDto;
import com.mat.repetere.model.Language;
import com.mat.repetere.model.NativeLanguage;
import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.language.LanguageFinder;
import com.mat.repetere.service.prompt.PromptGenerator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/prompts/generate")
public class CreatePromptController {

    private final PromptGenerator promptGenerator;
    private final LanguageFinder languageFinder;

    public CreatePromptController(PromptGenerator promptGenerator, LanguageFinder languageFinder) {
        this.promptGenerator = promptGenerator;
        this.languageFinder = languageFinder;
    }

    @PostMapping
    public String generatePrompt(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model, @ModelAttribute GeneratePromptRequestDto request){
        NativeLanguage nativeLanguage = loggedUser.getNativeLanguage();

        List<Language> languages = languageFinder.findAll();
        model.addAttribute("languages", languages);

        String generatedPrompt = promptGenerator.generatePrompt(request, nativeLanguage);

        model.addAttribute("generatedPrompt", generatedPrompt);

        return "prompts/generate :: content";
    }
}
