package com.mat.repetere.controller.deck;

import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.repository.DeckRepository;
import com.mat.repetere.service.deck.DeckFinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/decks/{id}/status")
public class ShowDeckStatusController {

    private final DeckFinder deckFinder;

    public ShowDeckStatusController(DeckFinder deckFinder) {
        this.deckFinder = deckFinder;
    }

    @GetMapping
    public String getDeckStatus(@PathVariable Long id, Model model){
        DeckResponseDto foundDeck = deckFinder.findById(id);
        model.addAttribute("deck", foundDeck);

        return "decks/fragments/deck-card :: deckCard";
    }
}
