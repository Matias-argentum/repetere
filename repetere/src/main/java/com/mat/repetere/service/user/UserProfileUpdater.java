package com.mat.repetere.service.user;

import com.mat.repetere.dto.user.UpdateEmailRequestDto;
import com.mat.repetere.dto.user.UpdateNameRequestDto;
import com.mat.repetere.dto.user.UpdatePasswordRequestDto;
import com.mat.repetere.dto.user.UserProfileResponseDto;
import com.mat.repetere.exception.UserNotFoundException;
import com.mat.repetere.exception.WrongPasswordException;
import com.mat.repetere.model.User;
import com.mat.repetere.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileUpdater {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public UserProfileUpdater(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProfileResponseDto updateName(UpdateNameRequestDto request, Long id){
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        System.out.println("User encontrado en service: " + user.getName());
        System.out.println("raw: " + request.passwordName());
        System.out.println("hash: " + user.getPassword());
        if (!passwordEncoder.matches(request.passwordName(), user.getPassword())){
            throw  new WrongPasswordException("Invalid Credentials");
        }

        user.setName(request.name());

        return UserProfileResponseDto.fromEntity(repository.save(user));

    }

    public void updateEmail(UpdateEmailRequestDto request, Long id){
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (!passwordEncoder.matches(request.passwordEmail(), user.getPassword())){
            throw  new WrongPasswordException("Invalid Credentials");
        }

        user.setEmail(request.email());

        repository.save(user);
    }

    public void updatePassword(UpdatePasswordRequestDto request, Long id){
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())){
            throw  new WrongPasswordException("Invalid Credentials");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setForcePasswordChange(false);
        repository.save(user);
    }
}
