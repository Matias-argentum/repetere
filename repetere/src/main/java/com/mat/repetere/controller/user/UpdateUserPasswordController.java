package com.mat.repetere.controller.user;

import com.mat.repetere.dto.user.UpdateEmailRequestDto;
import com.mat.repetere.dto.user.UpdatePasswordRequestDto;
import com.mat.repetere.dto.user.UserProfileResponseDto;
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
public class UpdateUserPasswordController {

    private final UserProfileUpdater userProfileUpdater;

    public UpdateUserPasswordController(UserProfileUpdater userProfileUpdater) {
        this.userProfileUpdater = userProfileUpdater;
    }


    @PostMapping("/update-password")
    public String updatePassword(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model, @ModelAttribute UpdatePasswordRequestDto request, HttpServletResponse response, HttpServletRequest httpServletRequest){
        Long id = loggedUser.getId();


        try {
            userProfileUpdater.updatePassword(request, id);
            httpServletRequest.getSession().invalidate();

            response.setHeader("HX-Redirect", "/login");
        } catch (WrongPasswordException e) {
            UserProfileResponseDto user = UserProfileResponseDto.toResponseFromPrincipal(loggedUser);

            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "Password incorrecto no se pudo actualizar password");
            return "user/profile :: content";
        }
        return "profile/profile :: content";
    }
}
