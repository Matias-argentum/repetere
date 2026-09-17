package com.mat.repetere.controller.user;

import com.mat.repetere.dto.user.UpdateEmailRequestDto;
import com.mat.repetere.security.CustomUserDetails;
import com.mat.repetere.service.user.UserProfileUpdater;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
    public void updateName(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute UpdateEmailRequestDto request, HttpServletResponse response, HttpServletRequest httpServletRequest){
        Long id = customUserDetails.getId();
        userProfileUpdater.updateEmail(request, id);
        httpServletRequest.getSession().invalidate();

        response.setHeader("HX-Redirect", "/login");
    }
}
