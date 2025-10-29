package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.ItemPedidoRequestDTO;
import com.alquimia.backend.dto.response.ItemEstoqueResponseDTO;
import com.alquimia.backend.dto.response.ItemPedidoResponseDTO;
import com.alquimia.backend.model.ItemEstoque;
import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Produto;
import com.alquimia.backend.repository.ItemEstoqueRepository;
import com.alquimia.backend.repository.ItemPedidoRepository;
import com.alquimia.backend.repository.PedidoRepository;
import com.alquimia.backend.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemEstoqueRepository itemEstoqueRepository;



    @Transactional
    public ItemPedidoResponseDTO cadastrarItemPedido(ItemPedidoRequestDTO itemPedidoRequestDTO) {
        Pedido pedido = pedidoRepository.findByCdPedido(itemPedidoRequestDTO.cdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com código: "
                + itemPedidoRequestDTO.cdPedido()));

      ;
        Produto produto = produtoRepository.findByCdProduto(itemPedidoRequestDTO.cdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com código: "
                + itemPedidoRequestDTO.cdProduto()));

        ItemEstoque itemEstoque = itemEstoqueRepository.findByCdProduto(produto).orElseThrow(() -> new RuntimeException("Produto não encontrado com código: "
                + itemPedidoRequestDTO.cdProduto()));;

        int qtItemPedido = itemPedidoRequestDTO.qtItemPedido();

        itemEstoque.reduzirQtdeItemEstoque(qtItemPedido);

        var itemPedido = new ItemPedido();
        BeanUtils.copyProperties(itemPedidoRequestDTO, itemPedido);
        itemPedido.setCdProduto(produto);
        itemPedido.setVlItemPedido(produto.getVlProduto());
        itemPedido.setCdPedido(pedido);
//        pedido.getItens().add(itemPedido);
        return new ItemPedidoResponseDTO(itemPedidoRepository.save(itemPedido)) ;
    }

    @Transactional
    public Integer removerItemDoPedido(Integer cdItemPedido) {
        ItemPedido itemPedido = itemPedidoRepository.findAllByCdItemPedido(cdItemPedido)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado."));

        int qtItemPedido = itemPedido.getQtItemPedido();
        Produto produto = itemPedido.getCdProduto();
        Integer cdPedidoAtual = itemPedido.getCdPedido().getCdPedido();

        Optional<ItemEstoque> itemEstoqueOptional = itemEstoqueRepository.findByCdProduto(produto);
        if (itemEstoqueOptional.isEmpty()) {
            throw new RuntimeException("Estoque não encontrado para o produto: " + produto.getCdProduto());
        }
        ItemEstoque itemEstoque = itemEstoqueOptional.get();
        itemEstoque.setQtItemEstoque(itemEstoque.getQtItemEstoque() + qtItemPedido);
        itemEstoqueRepository.save(itemEstoque);
        itemPedidoRepository.delete(itemPedido);

        return cdPedidoAtual;
    }

    public List<ItemPedidoResponseDTO> listarTodosItensPedido() {
        List<ItemPedidoResponseDTO> listaItensPedidoDTO = new ArrayList<>();
        List<ItemPedido> itens = itemPedidoRepository.findAll();
        for (ItemPedido itemPedido : itens) {
            listaItensPedidoDTO.add(new ItemPedidoResponseDTO(itemPedido));
        }
        return listaItensPedidoDTO;
    }

    @Transactional
    public void reverterEstoqueDoPedido(Pedido pedido) {
        List<ItemPedido> itensParaReverter = itemPedidoRepository.findAllByCdPedido(pedido);

        if (itensParaReverter.isEmpty()) {
            return;
        }

        for (ItemPedido itemPedido : itensParaReverter) {
            int qtItemPedido = itemPedido.getQtItemPedido();
            Produto produto = itemPedido.getCdProduto();

            Optional<ItemEstoque> itemEstoqueOptional = itemEstoqueRepository.findByCdProduto(produto);

            ItemEstoque itemEstoque = itemEstoqueOptional.get();

            itemEstoque.setQtItemEstoque(itemEstoque.getQtItemEstoque() + qtItemPedido);
            itemEstoqueRepository.save(itemEstoque);
        }

    }
}
