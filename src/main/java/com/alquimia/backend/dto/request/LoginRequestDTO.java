package com.alquimia.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Campo email não pode ser vazio")
        @Email
        String emailUsuario,

        @Size(min = 8, message = "Senha não pode ser menor que 8 caracteres")
        String senhaUsuario
) {
}
