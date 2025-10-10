package com.alquimia.backend.dto.request;

import com.alquimia.backend.enums.RoleUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @Size(min = 3, max = 100, message = "Nome do usuário não pode ser menor que 3 caracteres ou maior que 100 caracteres")
        String nmUsuario,

        @Size(min = 6, max = 100, message = "Email do usuário não pode ser menor que 6 caracteres ou maior que 100 caracteres")
        @Email(message = "Email inválido")
        String emailUsuario,

        @Size(min = 6, max = 100, message = "Senha do usuário não pode ser menor que 6 caracteres ou maior que 100 caracteres")
        String senhaUsuario,

        @NotBlank(message = "Role do usuário não pode ser vazia")
        RoleUsuario roleUsuario,

        @NotBlank
        String nuTelefone,

        @NotBlank
        String nuCpf


) {
}
