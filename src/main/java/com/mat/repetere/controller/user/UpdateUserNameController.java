package com.mat.repetere.controller.user;

import com.mat.repetere.dto.user.UpdateNameRequestDto;
import com.mat.repetere.dto.user.UserProfileResponseDto;
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
public class UpdateUserNameController {

    private final UserProfileUpdater userProfileUpdater;


    public UpdateUserNameController(UserProfileUpdater userProfileUpdater) {
        this.userProfileUpdater = userProfileUpdater;
    }

    @PostMapping("/update-name")
    public void updateName(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute UpdateNameRequestDto request, HttpServletRequest httpServletRequest, HttpServletResponse response){

        Long id = customUserDetails.getId();
        UserProfileResponseDto updatedUser = userProfileUpdater.updateName(request, id);
        httpServletRequest.getSession().invalidate();

        response.setHeader("HX-Redirect", "/login");

    }
}
