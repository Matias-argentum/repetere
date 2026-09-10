package com.mat.repetere.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class ShowLoginViewController {
    @GetMapping
    public String login() {
        return "login";
    }
}
