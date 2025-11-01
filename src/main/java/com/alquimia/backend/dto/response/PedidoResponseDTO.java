package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;

import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponseDTO(Integer cdPedido,
                                StatusPedido statusPedido,
                                TipoPagamento tipo,
                                Usuario cdUsuario,
                                List<ItemPedidoResponseDTO> itens) {
    public PedidoResponseDTO(Pedido pedido){
        this(pedido.getCdPedido(),
                pedido.getStatusPedido(),
                pedido.getTipoPagamento(),
                pedido.getCdUsuario(),
                pedido.getItens().stream().map(ItemPedidoResponseDTO::new).collect(Collectors.toList()));
    }
}
