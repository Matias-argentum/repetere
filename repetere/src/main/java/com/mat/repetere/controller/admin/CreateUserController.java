package com.mat.repetere.controller.admin;

import com.mat.repetere.dto.user.UserRequestDto;
import com.mat.repetere.dto.user.UserResponseDto;
import com.mat.repetere.exception.EmailAlreadyExistsException;
import com.mat.repetere.service.user.UserCreator;
import com.mat.repetere.service.user.UserFinder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class CreateUserController {
    private final UserCreator userCreator;
    private final UserFinder userFinder;

    public CreateUserController(UserCreator userCreator, UserFinder userFinder) {
        this.userCreator = userCreator;
        this.userFinder = userFinder;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createUser(@ModelAttribute UserRequestDto request, Model model, HttpServletResponse response){

        UserResponseDto created = null;
        try {
            created = userCreator.create(request);
            List<UserResponseDto> users = userFinder.findAll();
            model.addAttribute("users", users);
            model.addAttribute("successMessage", "User " + created.email() + " created!");
            response.setHeader("HX-Trigger", "closeModal"); // solo acá
        } catch (EmailAlreadyExistsException e){
            List<UserResponseDto> users = userFinder.findAll();
            model.addAttribute("users", users);
            model.addAttribute("errorMessage", "Error! El email ya está registrado!");
        }
        response.setHeader("HX-Trigger", "closeModal");
        return "admin/users :: users-table-div";
    }
}
