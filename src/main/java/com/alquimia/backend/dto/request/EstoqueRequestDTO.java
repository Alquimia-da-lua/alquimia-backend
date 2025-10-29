package com.alquimia.backend.dto.request;


import java.util.List;

public record EstoqueRequestDTO(

        Boolean isAtivo,
        List<ItemEstoqueRequestDTO> itens
) {
}
