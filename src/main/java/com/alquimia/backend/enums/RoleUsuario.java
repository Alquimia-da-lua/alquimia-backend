package com.alquimia.backend.enums;

public enum RoleUsuario {
    CLIENTE("cliente"),
    FUNCIONARIO("funcionario");

    private String roleUsuario;

    RoleUsuario(String roleUsuario) {
        this.roleUsuario = roleUsuario;
    }

    public String getRoleUsuario() {
        return roleUsuario;
    }
}
