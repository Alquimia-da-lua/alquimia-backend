package com.alquimia.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(
        @NotBlank(message = "Deve preencher o cep")
        @Valid
        String nuCep,
        @NotBlank(message = "Deve preencher o nome da rua")
        String dsLogradouro,
        String dsComplemento,
        String dsBairro,
        @NotBlank(message = "Deve preencher o nome da cidade")
        String dsLocalidade,
        @NotBlank(message = "Deve preencher o Estado")
        String nmEstado
) {
}
