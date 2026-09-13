package com.mat.repetere.controller.admin;

import com.mat.repetere.dto.user.UserResponseDto;
import com.mat.repetere.service.user.UserFinder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class ShowUsersListViewController {

    private final UserFinder userFinder;

    public ShowUsersListViewController(UserFinder userFinder) {
        this.userFinder = userFinder;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(HttpServletRequest request, Model model){
        List<UserResponseDto> users = userFinder.findAll();
        model.addAttribute("users", users);

        if (request.getHeader("HX-Request") != null) {
            return "admin/users :: content";
        }
        return "admin/users :: page";
    }
}
