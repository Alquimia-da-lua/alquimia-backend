package com.alquimia.backend.dto.response;

import com.alquimia.backend.model.ItemEstoque;
import jakarta.persistence.Column;

import java.util.List;

public record EstoqueResponseDTO(
        Integer cdEstoque,
        Boolean isAtivo,
        List<ItemEstoqueResponseDTO> itens
) {
}
