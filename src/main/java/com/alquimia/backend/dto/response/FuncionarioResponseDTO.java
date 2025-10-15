package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.RoleUsuario;

public record FuncionarioResponseDTO(
        Integer cdUsuario,
        String nmUsuario,
        String emailUsuario,
        RoleUsuario roleUsuario,
        String nuTelefone,
        boolean isAtivo,
        String nuCpf,
        Integer nuMatricula
) {
}
