package com.alquimia.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemEstoqueRequestDTO(
        @NotNull @Min(1)
        Integer cdProduto,
        @NotNull @Min(1)
        Integer cdEstoque,
        @NotNull @Min(0)
        Integer qtItemEstoque
) {
}
