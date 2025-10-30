package com.alquimia.backend.dto.request;

import com.alquimia.backend.model.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizarUsuarioRequestDTO(

        @Size(min = 3, max = 100, message = "Nome do usuário não pode ser menor que 3 caracteres ou maior que 100 caracteres")
        String nmUsuario,

        @Size(min = 6, max = 100, message = "Email do usuário não pode ser menor que 6 caracteres ou maior que 100 caracteres")
        @Email(message = "Email inválido")
        String emailUsuario,

        @Size(min = 8, message = "Senha do usuário não pode ser menor que 6 caracteres")
        String senhaUsuario,

        @Size(min = 10, max = 11, message = "Telefone do usuário deve conter entre 10 e 11 dígitos")
        String nuTelefone,

        @Size(min = 11, max = 11, message = "CPF do usuário deve conter 11 dígitos")
        String nuCpf,

        @NotBlank
        Endereco endereco
) {
}
