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
public class ToggleUserStatusController {
    private final UserUpdater userUpdater;

    public ToggleUserStatusController(UserUpdater userUpdater) {
        this.userUpdater = userUpdater;
    }

    @PatchMapping("/{id}/toggle-active")
    public String toggleStatus(@PathVariable Long id, Model model){
        UserResponseDto updatedUser = userUpdater.toggleActive(id);

        model.addAttribute("user", updatedUser);

        return "admin/fragments/user-row :: userRow";
    }
}
