package com.alquimia.backend.dto.response;


public record ItemEstoqueResponseDTO(
        Integer cdItemEstoque,
        Integer cdProduto,
        Integer cdEstoque,
        Integer qtItemEstoque
) {
}
