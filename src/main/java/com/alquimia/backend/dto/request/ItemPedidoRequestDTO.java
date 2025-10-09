package com.alquimia.backend.dto.request;

import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Produto;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
        @NotNull (message = "Não é possível salvar um item do pedido sem o código do pedido!")
        Pedido cdPedido,

        @NotNull(message = "Não é possível salvar um item do pedido sem o código do produto!")
        Produto cdProduto,

        @NotNull(message = "Não é possível salvar um item do pedido sem o valor!")
        Double vlItemPedido,

        @NotNull(message = "Não é possível salvar um item do pedido sem a quantidade!")
        Integer qtItemPedido
) {
}
