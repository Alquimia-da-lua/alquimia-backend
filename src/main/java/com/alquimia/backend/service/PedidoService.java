package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.PedidoRequestDTO;
import com.alquimia.backend.dto.response.PedidoResponseDTO;
import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;
import com.alquimia.backend.model.ItemPedido;
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

    @Transactional
    public Pedido atualizarValorPedido(Integer cdPedido) {
        Pedido pedido = buscarPedidoCd(cdPedido);

        List<ItemPedido> itens = itemPedidoRepository.findAllByCdPedido(pedido);
        double novoValorItens = itens.stream()
                .mapToDouble(item -> item.getVlItemPedido() * item.getQtItemPedido())
                .sum();

        pedido.setVlPedido(novoValorItens + pedido.getVlFrete());
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

    @Transactional
    public Pedido alterarStatusPedido(Integer cdPedido, StatusPedido statusPedido) {

        Pedido pedido = buscarPedidoCd(cdPedido);

        StatusPedido statusAtual = pedido.getStatusPedido();

        if (statusAtual == StatusPedido.CANCELADO || statusAtual == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Não é possível alterar o status. O pedido já está: " + statusAtual);
        }

        if (statusAtual == StatusPedido.EM_ANALISE && statusPedido == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Pedido Em Análise não pode ser alterado diretamente para Entregue.");
        }

        if (statusPedido == StatusPedido.CANCELADO) {
            itemPedidoService.reverterEstoqueDoPedido(pedido);

            pedido.setVlPedido(0.0);
            pedido.setVlFrete(0.0);
        }
        pedido.setStatusPedido(statusPedido);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido alterarTipoPagamento(Integer cdPedido, TipoPagamento novoTipoPagamento) {

        Pedido pedido = buscarPedidoCd(cdPedido);
        StatusPedido statusAtual = pedido.getStatusPedido();

        if (statusAtual == StatusPedido.CANCELADO || statusAtual == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Não é possível alterar o tipo de pagamento. O pedido já está " + statusAtual);
        }

        pedido.setTipoPagamento(novoTipoPagamento);
        return pedidoRepository.save(pedido);
    }

}
