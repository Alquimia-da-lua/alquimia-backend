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

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemEstoqueRepository itemEstoqueRepository;

    private ItemEstoque itemEstoque;

    @Transactional
    public ItemPedido cadastrarItemPedido(ItemPedidoRequestDTO itemPedidoRequestDTO) {

        Produto produtoDoDTO = itemPedidoRequestDTO.cdProduto();
        Produto produto = produtoRepository.findByCdProduto(produtoDoDTO.getCdProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com código: "
                + produtoDoDTO.getCdProduto()));

        int qtItemPedido = itemPedidoRequestDTO.qtItemPedido();
        itemEstoque.reduzirQtdeItemEstoque(qtItemPedido);

        var itemPedido = new ItemPedido();
        BeanUtils.copyProperties(itemPedidoRequestDTO, itemPedido);
        itemPedido.setCdProduto(produto);
        return itemPedidoRepository.save(itemPedido);
    }

    @Transactional
    public void  removerItemDoPedido(Integer cdItemPedido) {
        ItemPedido itemPedido = itemPedidoRepository.findAllByCdItemPedido(cdItemPedido)
                .orElseThrow(()-> new RuntimeException("Item do pedido não encontrado."));

        int qtItemPedido = itemPedido.getQtItemPedido();
        itemEstoque.aumentarQtdeItemEstoque(qtItemPedido);

        itemPedidoRepository.delete(itemPedido);
    }

    public List<ItemPedidoResponseDTO> listarItensPedido(Integer cdItemPedido) {
        List<ItemPedidoResponseDTO> listaItensPedidoDTO = new ArrayList<>();
        List<ItemPedido> pedido = itemPedidoRepository.findAll();
        for (ItemPedido itemPedido : pedido) {
            listaItensPedidoDTO.add(new ItemPedidoResponseDTO(itemPedido));
        }
        return listaItensPedidoDTO;
    }

}
