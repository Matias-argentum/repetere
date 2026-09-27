package com.mat.repetere.controller.dashboard;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class ShowDashboardViewController {
    @GetMapping
    public String dashboard(HttpServletRequest request) {
        if (request.getHeader("HX-Request") != null) {
            return "dashboard/dashboard :: content";
        }
        return "dashboard/dashboard :: page";
    }
}
