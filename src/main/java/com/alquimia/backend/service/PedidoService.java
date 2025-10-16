package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.PedidoRequestDTO;
import com.alquimia.backend.dto.response.PedidoResponseDTO;
import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.repository.ItemPedidoRepository;
import com.alquimia.backend.repository.PedidoRepository;
import com.alquimia.backend.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemPedidoService itemPedidoService;

    @Transactional
    public Pedido cadastrarPedido(PedidoRequestDTO pedidoRequestDTO) {
        var pedido = new Pedido();
        BeanUtils.copyProperties(pedidoRequestDTO, pedido);
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPedidoCd(Integer cdPedido){
        return pedidoRepository.findByCdPedido(cdPedido)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado com o código: " + cdPedido));
    }

    public List<Pedido> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public List<PedidoResponseDTO> listarPedidosPorCliente(Integer cdUsuario) {
        List<Pedido> pedidos = pedidoRepository.findAllByCdUsuario(cdUsuario);

        if (pedidos.isEmpty()) {
            throw new RuntimeException("Nenhum pedido encontrado para o cliente: " + cdUsuario);
        }
        return pedidos.stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

}
