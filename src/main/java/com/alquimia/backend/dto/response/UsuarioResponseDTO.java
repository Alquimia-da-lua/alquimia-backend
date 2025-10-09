package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.RoleUsuario;

public record UsuarioResponseDTO(
        Integer cdUsuario,
        String nmUsuario,
        String emailUsuario,
        RoleUsuario roleUsuario,
        String nuTelefone,
        boolean isAtivo,
        String nuCpf
) {
}
