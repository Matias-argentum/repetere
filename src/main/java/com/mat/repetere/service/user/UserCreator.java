package com.mat.repetere.service.user;

import com.mat.repetere.dto.user.UserRequestDto;
import com.mat.repetere.dto.user.UserResponseDto;
import com.mat.repetere.model.User;
import com.mat.repetere.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserCreator {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public UserCreator(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto create(UserRequestDto request){

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setForcePasswordChange(true);
        user.setNativeLanguage(request.nativeLanguage());
        user.setRole(request.role());
        user.setPassword(passwordEncoder.encode(LocalDate.now().getDayOfMonth() + "-" + request.name()));

        return UserResponseDto.fromEntity(repository.save(user));
    }
}
