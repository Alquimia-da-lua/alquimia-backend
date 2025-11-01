package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponseDTO(Integer cdPedido,
                                Usuario cdUsuario,
                                StatusPedido statusPedido,
                                List<ItemPedidoResponseDTO> itens) {
    public PedidoResponseDTO(Pedido pedido){
        this(pedido.getCdPedido(),
                pedido.getCdUsuario(),
                pedido.getStatusPedido(),
                pedido.getItens().stream().map(ItemPedidoResponseDTO::new).collect(Collectors.toList()));
    }
}
