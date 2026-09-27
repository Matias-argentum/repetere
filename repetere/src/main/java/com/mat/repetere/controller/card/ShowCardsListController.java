package com.mat.repetere.controller.card;

import com.mat.repetere.dto.card.CardResponseDto;
import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.model.Deck;
import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.card.CardSearcher;
import com.mat.repetere.service.deck.DeckFinder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/decks/{id}/cards")
public class ShowCardsListController {

    private final CardSearcher cardSearcher;
    private final DeckFinder deckFinder;

    public ShowCardsListController(CardSearcher cardSearcher, DeckFinder deckFinder) {
        this.cardSearcher = cardSearcher;
        this.deckFinder = deckFinder;
    }

    @GetMapping
    public String findAll(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model, HttpServletRequest request, @PathVariable Long id){
        DeckResponseDto deck = deckFinder.findById(id);
        if (!Objects.equals(deck.userId(), loggedUser.getId())){
            throw new RuntimeException("Not allowed");
        }

        List<CardResponseDto> cardList = cardSearcher.findAllByDeckId(id);

        model.addAttribute("deckName", deck.name());
        model.addAttribute("cards", cardList);

        if (request.getHeader("HX-Request") != null) {
            return "cards/card-list :: content";
        }
        return "cards/card-list :: page";
    }
}
