package com.alquimia.backend.dto.request;

import com.alquimia.backend.enums.CategoriaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ProdutoRequestDTO(
        @NotBlank(message = "Deve preencher o nome do produto")
        String nmProduto,

        String dsProduto,

        @NotNull Double vlProduto,

        @NotNull Integer cdUsuario,

        @NotNull(message = "Deve ser preenchida uma categoria")
        CategoriaEnum categoria) {
}
