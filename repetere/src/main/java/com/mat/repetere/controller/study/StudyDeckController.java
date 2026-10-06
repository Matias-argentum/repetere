package com.mat.repetere.controller.study;

import com.mat.repetere.dto.study.StudyCardDto;
import com.mat.repetere.service.study.CardReviewProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/decks/{deckId}/study/rate")
public class StudyDeckController {

    private final CardReviewProcessor cardReviewProcessor;

    public StudyDeckController(CardReviewProcessor cardReviewProcessor) {
        this.cardReviewProcessor = cardReviewProcessor;
    }

    @PostMapping
    public String rate(@PathVariable Long deckId, @RequestParam Long cardId, @RequestParam int rating, Model model) {
        StudyCardDto nextCard = cardReviewProcessor.submitRating(cardId, deckId, rating);
        model.addAttribute("deckId", deckId);
        if (nextCard != null) {
            model.addAttribute("card", nextCard);
        }
        return "study/study :: content";
    }

}
