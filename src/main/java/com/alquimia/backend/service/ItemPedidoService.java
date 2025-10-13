package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.ItemPedidoRequestDTO;
import com.alquimia.backend.dto.response.ItemPedidoResponseDTO;
import com.alquimia.backend.model.ItemEstoque;
import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.model.Produto;
import com.alquimia.backend.repository.ItemEstoqueRepository;
import com.alquimia.backend.repository.ItemPedidoRepository;
import com.alquimia.backend.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemEstoqueRepository itemEstoqueRepository;

    @Transactional
    public ItemPedido cadastrarItemPedido(ItemPedidoRequestDTO itemPedidoRequestDTO) {

        Produto produtoDoDTO = itemPedidoRequestDTO.cdProduto();
        Produto produto = produtoRepository.findByCdProduto(produtoDoDTO.getCdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com código: "
                + produtoDoDTO.getCdProduto()));

//        ItemEstoque  itemEstoque = itemEstoqueRepository.findByCdProduto(produto)
//                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto: " + cdProduto));
//
//        int qtPedida = itemPedidoRequestDTO.qtItemPedido();
//
//        if (itemEstoque.getQtItemEstoque() < qtPedida) {
//            throw new RuntimeException("Estoque insuficiente, somente: " + itemEstoque.getQtItemEstoque()
//            + " disponível.");
//        }
//
//        itemEstoque.setQtItem(itemEstoque.getQtItem() - quantidadePedida);
//        itemEstoqueRepository.save(itemEstoque);

        var itemPedido = new ItemPedido();
        BeanUtils.copyProperties(itemPedidoRequestDTO, itemPedido);
        itemPedido.setCdProduto(produto);
        return itemPedidoRepository.save(itemPedido);
    }
}
