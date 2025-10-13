package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.PedidoRequestDTO;
import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.repository.ItemPedidoRepository;
import com.alquimia.backend.repository.PedidoRepository;
import com.alquimia.backend.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Pedido cadastrarPedido(PedidoRequestDTO pedidoRequestDTO) {
        var pedido = new Pedido();
        BeanUtils.copyProperties(pedidoRequestDTO, pedido);
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPedido(Integer cdPedido){
        return pedidoRepository.findByCdPedido(cdPedido)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado."));
    }


}
