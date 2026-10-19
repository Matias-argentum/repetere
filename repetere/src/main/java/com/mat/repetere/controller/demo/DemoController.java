package com.mat.repetere.controller;

import com.mat.repetere.dto.demo.DemoCardDto;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController {



    private final List<DemoCardDto> demoCards = List.of(
            new DemoCardDto(
                    "offer",
                    "/ˈɒf.ər/",
                    "Oferta",
                    "I'm going to make him an offer he can't refuse.",
                    "Le voy a hacer una oferta que no podrá rechazar.",
                    "/audio/demo/offer.mp3",
                    "/audio/demo/offer_sentence.mp3"
            ),
            new DemoCardDto(
                    "life",
                    "/laɪf/",
                    "Vida",
                    "Life is like a box of chocolates... you never know what you're gonna get.",
                    "La vida es como una caja de bombones... nunca sabes lo que te va a tocar.",
                    "/audio/demo/life.mp3",
                    "/audio/demo/life_sentence.mp3"
            ),
            new DemoCardDto(
                    "responsibility",
                    "/rɪˌspɒn.səˈbɪl.ə.ti/",
                    "Responsabilidad",
                    "With great power comes great responsibility.",
                    "Un gran poder conlleva una gran responsabilidad.",
                    "/audio/demo/responsibility.mp3",
                    "/audio/demo/responsibility_sentence.mp3"
            )
    );

    @GetMapping
    public String showDemo(Authentication authentication, Model model) {
        // Verifica si el usuario ya está autenticado
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        model.addAttribute("card", demoCards.get(0));
        model.addAttribute("nextStep", 1);
        return "demo/index";
    }

    @GetMapping("/rate")
    public String rateCard(@RequestParam int step, Model model) {
        if (step < demoCards.size()) {
            model.addAttribute("card", demoCards.get(step));
            model.addAttribute("nextStep", step + 1);
        } else {
            model.addAttribute("card", null);
        }
        return "demo/card-fragment :: content";
    }
}