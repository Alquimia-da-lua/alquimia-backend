package com.alquimia.backend.dto.request;

import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(

        @NotNull(message = "Não é possível salvar um pedido sem um cliente!")
        Integer cdUsuario,

        @NotNull(message = "Não é possível salvar um pedido sem o valor!")
        Double vlPedido,

        @NotNull(message = "Não é possível salvar um pedido sem o frete!")
        Double vlFrete,

        @NotNull(message = "Não é possível salvar um pedido sem um tipo de pagamento")
        TipoPagamento tipoPagamento
) {
}
