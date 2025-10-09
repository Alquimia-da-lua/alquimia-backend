package com.alquimia.backend.dto.request;

import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;
import com.alquimia.backend.model.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(

        @NotNull(message = "Não é possível salvar um peddo sem um cliente!")
        Cliente cdCliente,

        @NotNull(message = "Não é possível salvar um pedido sem o valor!")
        Double vlPedido,

        @NotNull(message = "Não é possível salvar um pedido sem o frete!")
        Double vlFrete,

        @NotBlank(message = "Não é possível salvar um pedido sem status!")
        StatusPedido statusPedido,

        @NotBlank(message = "Não é possível salvar um pedido sem um tipo de pagamento")
        TipoPagamento tipoPagamento
) {
}
