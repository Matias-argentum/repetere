package com.mat.repetere.controller.deck;

import com.mat.repetere.model.Language;
import com.mat.repetere.service.language.LanguageFinder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/decks/new")
public class ShowDeckFormViewController {

    private final LanguageFinder languageFinder;

    public ShowDeckFormViewController(LanguageFinder languageFinder) {
        this.languageFinder = languageFinder;
    }

    @GetMapping
    public String showDeckFormView(Model model, HttpServletRequest request){
        List<Language> languages = languageFinder.findAll();
        model.addAttribute("languages", languages);


        if (request.getHeader("HX-Request") != null) {
            return "decks/new-deck :: content";
        }
        return "decks/new-deck :: page";
    }
}
