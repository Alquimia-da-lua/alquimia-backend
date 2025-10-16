package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;
import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Produto;

public record ItemPedidoResponseDTO(Integer cdItemPedido,
                                    Pedido cdPedido,
                                    ProdutoResponseDTO cdProduto,
                                    Double vlItemPedido,
                                    Integer qtItemPedido) {
    public ItemPedidoResponseDTO(ItemPedido itemPedido){
        this(
                itemPedido.getCdItemPedido(),
                itemPedido.getCdPedido(),
                new ProdutoResponseDTO(itemPedido.getCdProduto()),
                itemPedido.getVlItemPedido(),
                itemPedido.getQtItemPedido());
    }
}
