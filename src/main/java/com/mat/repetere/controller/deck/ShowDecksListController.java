package com.mat.repetere.controller.deck;

import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.deck.DeckFinder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/decks")
public class ShowDecksListController {
    private final DeckFinder deckFinder;

    public ShowDecksListController(DeckFinder deckFinder) {
        this.deckFinder = deckFinder;
    }

    @GetMapping
    public String findAll(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model, HttpServletRequest request){
        List<DeckResponseDto> decks = deckFinder.findAllByUserId(loggedUser.getId());
        model.addAttribute("decks", decks);

        if (request.getHeader("HX-Request") != null) {
            return "decks/deck-list :: content";
        }
        return "decks/deck-list :: page";
    }
}
