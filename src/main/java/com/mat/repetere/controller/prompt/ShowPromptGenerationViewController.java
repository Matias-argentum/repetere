package com.mat.repetere.controller.prompt;

import com.mat.repetere.model.Language;
import com.mat.repetere.service.language.LanguageFinder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/prompts/generate")
public class ShowPromptGenerationViewController {
    private final LanguageFinder languageFinder;

    public ShowPromptGenerationViewController(LanguageFinder languageFinder) {
        this.languageFinder = languageFinder;
    }

    @GetMapping
    public String showPromptGenerationView(Model model, HttpServletRequest request){

        List<Language> languages = languageFinder.findAll();
        model.addAttribute("languages", languages);


        if (request.getHeader("HX-Request") != null) {
            return "prompts/generate :: content";
        }
        return "prompts/generate :: page";

    }
}
