package com.mat.repetere.service.user;

import com.mat.repetere.dto.user.UserResponseDto;
import com.mat.repetere.exception.UserNotFoundException;
import com.mat.repetere.model.User;
import com.mat.repetere.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserUpdater {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserUpdater(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto toggleActive(Long id){
        User user = repository.findById(id).orElseThrow(()-> new UserNotFoundException(id));

        user.setActive(!user.getActive());

        return UserResponseDto.fromEntity(repository.save(user));
    }

    public UserResponseDto forcePasswordReset(Long id){
        User user = repository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        user.setPassword(passwordEncoder.encode(LocalDate.now().getDayOfMonth() + "-" + user.getName()));

        return UserResponseDto.fromEntity(repository.save(user));
    }
}
