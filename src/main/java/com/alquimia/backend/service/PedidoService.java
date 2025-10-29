package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.PedidoRequestDTO;
import com.alquimia.backend.dto.response.PedidoResponseDTO;
import com.alquimia.backend.dto.response.ProdutoResponseDTO;
import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;
import com.alquimia.backend.exception.ProdutoNaoEncontradoException;
import com.alquimia.backend.exception.UsuarioNaoEncontradoException;
import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.*;
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
    private ItemPedidoService itemPedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;



    @Transactional
    public PedidoResponseDTO cadastrarPedido(PedidoRequestDTO pedidoRequestDTO) {
        var pedido = new Pedido();
        var usuario = usuarioRepository.findByCdUsuario(pedidoRequestDTO.cdUsuario()).orElseThrow(UsuarioNaoEncontradoException::new);
        BeanUtils.copyProperties(pedidoRequestDTO, pedido);
        pedido.setCdUsuario(usuario);
        pedido.setStatusPedido(StatusPedido.EM_ANALISE);
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponseDTO atualizarValorPedido(Integer cdPedido) {
        Pedido pedido = this.pedidoRepository.findByCdPedido(cdPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o código: " + cdPedido));

        List<ItemPedido> itens = itemPedidoRepository.findAllByCdPedido(pedido);
        double novoValorItens = itens.stream()
                .mapToDouble(item -> item.getVlItemPedido() * item.getQtItemPedido())
                .sum();

        pedido.setVlPedido(novoValorItens + pedido.getVlFrete());
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponseDTO buscarPedidoCd(Integer cdPedido){
        return this.pedidoRepository.findByCdPedido(cdPedido)
                .map(PedidoResponseDTO::new)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o código: " + cdPedido));

    }

    @Transactional
    public List<PedidoResponseDTO> listarPedidos(){
        List<PedidoResponseDTO> pedidos = new ArrayList<>();
        List<Pedido> model = this.pedidoRepository.findAll();
        for (Pedido pedido : model) {
            pedidos.add(new PedidoResponseDTO(pedido));
        }
        return pedidos;
    }

    @Transactional
    public List<PedidoResponseDTO> listarPedidosPorCliente(Usuario cdUsuario) {
        List<Pedido> pedidos = pedidoRepository.findAllByCdUsuario(cdUsuario);

        if (pedidos.isEmpty()) {
            throw new RuntimeException("Nenhum pedido encontrado para o cliente: " + cdUsuario);
        }
        return pedidos.stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponseDTO alterarStatusPedido(Integer cdPedido, StatusPedido statusPedido) {
        Pedido pedido = this.pedidoRepository.findByCdPedido(cdPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o código: " + cdPedido));


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
        return new PedidoResponseDTO(pedidoRepository.save(pedido)) ;
    }

    @Transactional
    public PedidoResponseDTO alterarTipoPagamento(Integer cdPedido, TipoPagamento novoTipoPagamento) {
        Pedido pedido = this.pedidoRepository.findByCdPedido(cdPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o código: " + cdPedido));

        StatusPedido statusAtual = pedido.getStatusPedido();

        if (statusAtual == StatusPedido.CANCELADO || statusAtual == StatusPedido.ENTREGUE) {
            throw new RuntimeException("Não é possível alterar o tipo de pagamento. O pedido já está " + statusAtual);
        }

        pedido.setTipoPagamento(novoTipoPagamento);
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }

}
