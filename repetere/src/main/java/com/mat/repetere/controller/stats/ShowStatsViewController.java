package com.mat.repetere.controller.stats;

import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.stats.StatsSearcherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stats")
public class ShowStatsViewController {

    private final StatsSearcherService statsSearcherService;

    public ShowStatsViewController(StatsSearcherService statsSearcherService) {
        this.statsSearcherService = statsSearcherService;
    }

    @GetMapping
    public String showStatsView(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model, HttpServletRequest request){

        model.addAttribute("stats", statsSearcherService.getUserStats(loggedUser.getId()));

        if (request.getHeader("HX-Request") != null) {
            return "stats/stats :: content";
        }
        return "stats/stats :: page";

    }
}
