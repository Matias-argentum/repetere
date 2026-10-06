package com.mat.repetere.controller.study;

import com.mat.repetere.dto.study.StudyCardDto;
import com.mat.repetere.service.card.CardSearcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/decks/{id}/study")
public class ShowStudyViewController {

    private final CardSearcher cardSearcher;

    public ShowStudyViewController(CardSearcher cardSearcher) {
        this.cardSearcher = cardSearcher;
    }

    @GetMapping
    public String showStudyView(@PathVariable Long id, Model model, HttpServletRequest request){

        Optional<StudyCardDto> nextCard = cardSearcher.findNextDueCard(id);
        model.addAttribute("deckId", id);
        System.out.println("Decl id ---> " + id);

        if (nextCard.isPresent()) {
            model.addAttribute("card", nextCard.get());
            System.out.println(nextCard.get());
        }

        if (request.getHeader("HX-Request") != null) {
            return "study/study :: content";
        }
        return "study/study :: page";
    }
}
