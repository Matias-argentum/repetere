package com.mat.repetere.controller.deck;

import com.mat.repetere.dto.deck.DeckRequestDto;
import com.mat.repetere.dto.deck.DeckResponseDto;
import com.mat.repetere.dto.deck.ParsedCsvLineDto;
import com.mat.repetere.exception.MalformattedCsvFileException;
import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.deck.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/decks/new")
public class CreateDeckController {

    private final DeckCsvParser deckCsvParser;
    private final DeckCreator deckCreator;
    private final DeckSearcher deckSearcher;
    private final DeckAudioProcessor deckAudioProcessor;

    public CreateDeckController(DeckCsvParser deckCsvParser, DeckCreator deckCreator, DeckSearcher deckSearcher, DeckAudioProcessor deckAudioProcessor) {
        this.deckCsvParser = deckCsvParser;
        this.deckCreator = deckCreator;
        this.deckSearcher = deckSearcher;
        this.deckAudioProcessor = deckAudioProcessor;
    }

    @PostMapping
    public String createDeck(@AuthenticationPrincipal CustomUserDetails loggedUser,  @ModelAttribute DeckRequestDto request, Model model, @RequestParam("deckCsv")MultipartFile deckCsv){

        System.out.println("Archivo recibido con tamaño: " + deckCsv.getSize() + " y contentType: " + deckCsv.getContentType());
        try{
            List<ParsedCsvLineDto> parsedData = deckCsvParser.parse(deckCsv);
            DeckResponseDto createdDeck = deckCreator.create(request, parsedData, loggedUser.getId());
            deckAudioProcessor.generateAudios(createdDeck.id());
            List<DeckResponseDto> decks = deckSearcher.findAllByUserId(loggedUser.getId());
            model.addAttribute("createdDeck", createdDeck);
            model.addAttribute("decks", decks);
            return "decks/deck-list :: content";
        } catch (MalformattedCsvFileException | IOException e) {
            model.addAttribute("errorMessage", "Error en el archivo: " + e.getMessage());
            return "decks/new :: content";
        }
    }
}
