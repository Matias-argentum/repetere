package com.mat.repetere.dto.user;

public record UpdatePasswordRequestDto(
        String currentPassword,
        String newPassword
) {
}
