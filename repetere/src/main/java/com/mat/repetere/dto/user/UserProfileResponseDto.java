package com.mat.repetere.dto.user;

import com.mat.repetere.model.NativeLanguage;
import com.mat.repetere.model.Role;
import com.mat.repetere.model.User;
import com.mat.repetere.security.CustomUserDetails;

public record UserProfileResponseDto(
        Long id,
        String name,
        String email,
        NativeLanguage nativeLanguage,
        Role role
) {

    public static UserProfileResponseDto fromEntity(User user){
        return new UserProfileResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getNativeLanguage(),
                user.getRole()
        );
    }

    public static UserProfileResponseDto toResponseFromPrincipal(CustomUserDetails principal){
        return new UserProfileResponseDto(
                principal.getId(),
                principal.getName(),
                principal.getEmail(),
                principal.getNativeLanguage(),
                principal.getRole()
        );
    }
}
