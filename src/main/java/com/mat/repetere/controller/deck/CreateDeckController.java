package com.mat.repetere.controller.deck;

import com.mat.repetere.dto.deck.DeckRequestDto;
import com.mat.repetere.service.language.LanguageFinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/decks/new")
public class CreateDeckController {

    @PostMapping
    public String createDeck(@ModelAttribute DeckRequestDto request, Model model, @RequestParam("deckCsv")MultipartFile deckCsv){

        System.out.println("Archivo recibido con tamaño: " + deckCsv.getSize() + " y contentType: " + deckCsv.getContentType());

        return null;
    }
}
