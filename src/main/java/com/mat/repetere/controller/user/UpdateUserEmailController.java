package com.mat.repetere.controller.user;

import com.mat.repetere.dto.user.UpdateEmailRequestDto;
import com.mat.repetere.dto.user.UserProfileResponseDto;
import com.mat.repetere.exception.EmailAlreadyExistsException;
import com.mat.repetere.exception.WrongPasswordException;
import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.user.UserProfileUpdater;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UpdateUserEmailController {

    private final UserProfileUpdater userProfileUpdater;

    public UpdateUserEmailController(UserProfileUpdater userProfileUpdater) {
        this.userProfileUpdater = userProfileUpdater;
    }

    @PostMapping("/update-email")
    public String updateEmail(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model, @ModelAttribute UpdateEmailRequestDto request, HttpServletResponse response, HttpServletRequest httpServletRequest){
        Long id = loggedUser.getId();

        try {
            userProfileUpdater.updateEmail(request, id);
            httpServletRequest.getSession().invalidate();

            response.setHeader("HX-Redirect", "/login");
        } catch (WrongPasswordException | EmailAlreadyExistsException e) {
            UserProfileResponseDto user = UserProfileResponseDto.toResponseFromPrincipal(loggedUser);

            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "Password incorrecto o email duplicado");
            return "user/profile :: content";
        }
        return "profile/profile :: content";
    }
}
