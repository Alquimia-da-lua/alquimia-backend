package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.RoleUsuario;
import com.alquimia.backend.model.Usuario;

public record UsuarioResponseDTO(
        Integer cdUsuario,
        String nmUsuario,
        String emailUsuario,
        RoleUsuario roleUsuario,
        String nuTelefone,
        boolean isAtivo,
        String nuCpf
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getEmailUsuario(),
                usuario.getRoleUsuario(),
                usuario.getNuTelefone(),
                usuario.isAtivo(),
                usuario.getNuCpf()
        );
    }
}
