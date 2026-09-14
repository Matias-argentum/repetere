package com.mat.repetere.dto.user;

import com.mat.repetere.model.NativeLanguage;
import com.mat.repetere.model.Role;

public record UserRequestDto(
        String name,
        String email,
        NativeLanguage nativeLanguage,
        Role role
) {
}
