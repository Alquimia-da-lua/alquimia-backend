package com.alquimia.backend.dto.response;

import com.alquimia.backend.model.Estoque;
import com.alquimia.backend.model.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record ItemEstoqueResponseDTO(
        Integer cdItemEstoque,
        Integer cdProduto,
        Integer cdEstoque,
        Integer qtItemEstoque
) {
}
