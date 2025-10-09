package com.alquimia.backend.dto.request;

import com.alquimia.backend.model.ItemEstoque;
import jakarta.persistence.Column;

import java.util.List;

public record EstoqueRequestDTO(

        Boolean isAtivo,
        List<ItemEstoqueRequestDTO> itens
) {
}
