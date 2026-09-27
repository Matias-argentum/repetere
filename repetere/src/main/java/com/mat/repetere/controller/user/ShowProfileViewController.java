package com.mat.repetere.controller.user;

import com.mat.repetere.dto.user.UserProfileResponseDto;
import com.mat.repetere.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ShowProfileViewController {

    @GetMapping
    public String showProfile(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model, HttpServletRequest request){
        UserProfileResponseDto user = new UserProfileResponseDto(
                loggedUser.getId(),
                loggedUser.getName(),
                loggedUser.getEmail(),
                loggedUser.getNativeLanguage(),
                loggedUser.getRole()
        );

        model.addAttribute("user", user);

        if (request.getHeader("HX-Request") != null) {
            return "user/profile :: content";
        }
        return "user/profile :: page";
    }
}
