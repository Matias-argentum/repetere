package com.mat.repetere.controller.admin;

import com.mat.repetere.dto.user.UserResponseDto;
import com.mat.repetere.service.user.UserUpdater;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class ForcePasswordResetController {
    private final UserUpdater userUpdater;


    public ForcePasswordResetController(UserUpdater userUpdater) {
        this.userUpdater = userUpdater;
    }

    @PatchMapping("/{id}/reset-password")
    public String forcePasswordUpdate(@PathVariable Long id, Model model){
        UserResponseDto updatedUser = userUpdater.forcePasswordReset(id);

        model.addAttribute("user", updatedUser);

        return "admin/fragments/user-row :: userRow";
    }
}
