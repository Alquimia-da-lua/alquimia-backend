package com.alquimia.backend.dto.response;

import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponseDTO(Integer cdPedido,
                                UsuarioResponseDTO cdUsuario,
                                List<ItemPedidoResponseDTO> itens) {
    public PedidoResponseDTO(Pedido pedido){
        this(pedido.getCdPedido(),
                new UsuarioResponseDTO(pedido.getCdUsuario()),
                pedido.getItens().stream().map(ItemPedidoResponseDTO::new).collect(Collectors.toList()));
    }
}
