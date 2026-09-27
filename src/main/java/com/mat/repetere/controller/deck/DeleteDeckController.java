package com.mat.repetere.controller.deck;

import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.deck.DeckDeleter;
import com.mat.repetere.service.deck.DeckFinder;
import com.mat.repetere.service.deck.DeckSearcher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/decks/{id}")
public class DeleteDeckController {

    private final DeckDeleter deckDeleter;
    private final DeckFinder deckFinder;
    private final DeckSearcher deckSearcher;

    public DeleteDeckController(DeckDeleter deckDeleter, DeckFinder deckFinder, DeckSearcher deckSearcher) {
        this.deckDeleter = deckDeleter;
        this.deckFinder = deckFinder;
        this.deckSearcher = deckSearcher;
    }

    @DeleteMapping
    public String deleteDeck(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails loggedUser, Model model){
        DeckResponseDto foundDeck = deckFinder.findById(id);
        if (!Objects.equals(foundDeck.userId(), loggedUser.getId())){
            throw new RuntimeException("Invalid id");
        }
        deckDeleter.delete(id);

        List<DeckResponseDto> decks = deckSearcher.findAllByUserId(loggedUser.getId());
        model.addAttribute("decks", decks);
        model.addAttribute("successMessage", "Mazo '" + foundDeck.name() + "' eliminado correctamente");

        return "decks/deck-list :: content";

    }
}
