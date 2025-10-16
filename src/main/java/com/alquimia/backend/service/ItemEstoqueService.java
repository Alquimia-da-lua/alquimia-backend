package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.ItemEstoqueRequestDTO;
import com.alquimia.backend.dto.response.ItemEstoqueResponseDTO;
import com.alquimia.backend.model.ItemEstoque;
import com.alquimia.backend.repository.EstoqueRepository;
import com.alquimia.backend.repository.ItemEstoqueRepository;
import com.alquimia.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class  ItemEstoqueService {

    @Autowired
    ItemEstoqueRepository itemEstoqueRepository;

    @Autowired
    EstoqueRepository estoqueRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Transactional
    public ItemEstoqueResponseDTO criarOuAtualizarItem(ItemEstoqueRequestDTO dto){
        var estoque = estoqueRepository.findByCdEstoque(dto.cdEstoque())
                .orElseThrow(()-> new IllegalArgumentException("Estoque não encontrado"));

        var produto = produtoRepository.findByCdProduto(dto.cdProduto())
                .orElseThrow(()-> new IllegalArgumentException("Produto não cadastrado!"));

        var itemSelecionado = itemEstoqueRepository.findByCdEstoque_CdEstoqueAndCdProduto_CdProduto(dto.cdEstoque(), dto.cdProduto());

        ItemEstoque item;//ref do item (novo ou nao

        if (itemSelecionado.isEmpty()){
             item = new ItemEstoque();

             item.setCdEstoque(estoque);
             item.setCdProduto(produto);
             item.setQtItemEstoque(dto.qtItemEstoque());

             estoque.getItens().add(item);

             item = itemEstoqueRepository.save(item);
        }else{
            item = itemSelecionado.get();

            item.setQtItemEstoque(dto.qtItemEstoque());
        }

        return new ItemEstoqueResponseDTO(
            item.getCdItemEstoque(),
                item.getCdProduto().getCdProduto(),
                item.getCdEstoque().getCdEstoque(),
                item.getQtItemEstoque()
        );
    }

    @Transactional(readOnly = true)
    public ItemEstoqueResponseDTO buscarItemEstoquePorId (Integer cdItemEstoque){
        var item = itemEstoqueRepository.findByCdItemEstoque(cdItemEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Item não encontrado"));

        return new ItemEstoqueResponseDTO(
                item.getCdItemEstoque(),
                item.getCdProduto().getCdProduto(),
                item.getCdEstoque().getCdEstoque(),
                item.getQtItemEstoque()
        );
    }

    @Transactional(readOnly = true)
    public List<ItemEstoqueResponseDTO> listarItensDoEstoque (Integer cdEstoque){
        var estoque = estoqueRepository.findByCdEstoque(cdEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Estoque não encontrado"));

        if(!Boolean.TRUE.equals(estoque.getIsAtivo())) throw new IllegalStateException("Estoque inativo");

        return itemEstoqueRepository.findByCdEstoque_CdEstoqueAndCdEstoque_IsAtivoTrue(cdEstoque)
                .stream()
                .map(i -> new ItemEstoqueResponseDTO(
                        i.getCdItemEstoque(),
                        i.getCdProduto().getCdProduto(),
                        i.getCdEstoque().getCdEstoque(),
                        i.getQtItemEstoque()
                )).toList();
    }

    @Transactional
    public void incrementarItemEstoque(Integer cdItemEstoque, Integer qtde){
        if (qtde < 0) throw new IllegalArgumentException("A quantidade deve ser maior que zero.");

        var item = itemEstoqueRepository.findByCdItemEstoque(cdItemEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Item não encontrado no estoque"));

        item.aumentarQtdeItemEstoque(qtde);
    }

    @Transactional
    public void decrementarItemEstoque(Integer cdItemEstoque, Integer qtde){
        if (qtde < 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");

        var item = itemEstoqueRepository.findByCdItemEstoque(cdItemEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Item não encontrado no estoque."));

        item.reduzirQtdeItemEstoque(qtde);
    }

    public void deletarItemEstoque(Integer cdItemEstoque){
        var item = itemEstoqueRepository.findByCdItemEstoque(cdItemEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Item não existe no estoque"));

        itemEstoqueRepository.delete(item);
    }

}
