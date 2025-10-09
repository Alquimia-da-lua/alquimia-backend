package com.alquimia.backend.dto.request;

import com.alquimia.backend.model.Estoque;
import com.alquimia.backend.model.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemEstoqueRequestDTO(
        @NotNull @Min(1)
        Integer cdProduto,
        @NotNull @Min(1)
        Integer cdEstoque,
        @NotNull @Min(1)
        Integer qtItemEstoque
) {
}
