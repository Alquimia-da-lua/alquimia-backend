package com.alquimia.backend.enums;

import lombok.Getter;

@Getter
public enum RoleUsuario {
    CLIENTE("cliente"),
    FUNCIONARIO("funcionario");

    private final String roleUsuario;

    RoleUsuario(String roleUsuario) {
        this.roleUsuario = roleUsuario;
    }
}
