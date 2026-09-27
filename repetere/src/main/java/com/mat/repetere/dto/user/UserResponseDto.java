package com.mat.repetere.dto.user;

import com.mat.repetere.model.NativeLanguage;
import com.mat.repetere.model.Role;
import com.mat.repetere.model.User;

import java.time.LocalDateTime;

public record UserResponseDto (
        Long id,
        String name,
        String email,
        NativeLanguage nativeLanguage,
        Role role,
        boolean active,
        boolean forcePasswordChange

){
    public static UserResponseDto fromEntity(User user){
        return new UserResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getNativeLanguage(),
                    user.getRole(),
                    user.getActive(),
                    user.getForcePasswordChange()
                );

    }
}
