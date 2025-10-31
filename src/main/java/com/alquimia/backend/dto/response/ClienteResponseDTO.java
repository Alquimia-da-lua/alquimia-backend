package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.RoleUsuario;
import com.alquimia.backend.model.Cliente;
import com.alquimia.backend.model.Endereco;

public record ClienteResponseDTO(
        Integer cdUsuario,
        String nmUsuario,
        String emailUsuario,
        RoleUsuario roleUsuario,
        String nuTelefone,
        boolean isAtivo,
        String nuCpf,
        Endereco endereco ) {
    public ClienteResponseDTO(Cliente cliente) {
        this(
                cliente.getCdUsuario(),
                cliente.getNmUsuario(),
                cliente.getEmailUsuario(),
                cliente.getRoleUsuario(),
                cliente.getNuTelefone(),
                cliente.isAtivo(),
                cliente.getNuCpf(),
                cliente.getCdEndereco()
        );
    }
}
