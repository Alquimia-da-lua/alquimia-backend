package com.alquimia.backend.dto.response;

public record RefreshTokenResponseDTO(
        String token,
        String refreshToken
) {
}
