package com.alquimia.backend.dto.response;

import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.model.Pedido;

import java.util.List;

public record PedidoResponseDTO(Integer cdPedido,
                                List<ItemPedido> itens) {
    public PedidoResponseDTO(Pedido pedido){
        this(pedido.getCdPedido(),
                pedido.getItens());
    }
}
