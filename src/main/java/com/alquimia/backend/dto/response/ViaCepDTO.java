package com.alquimia.backend.dto.response;

public record ViaCepDTO(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf
) {
}
