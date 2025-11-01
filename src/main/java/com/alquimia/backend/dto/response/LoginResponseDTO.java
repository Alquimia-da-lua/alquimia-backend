package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.RoleUsuario;

public record LoginResponseDTO(
        Integer cdUsuario,
        String nmUsuario,
        String emailUsuario,
        String nuTelefone,
        RoleUsuario roleUsuario,
        String token,
        String refreshToken
) {
}
