package com.mat.repetere.dto.user;

import com.mat.repetere.model.NativeLanguage;
import com.mat.repetere.model.Role;
import com.mat.repetere.model.User;

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
}
