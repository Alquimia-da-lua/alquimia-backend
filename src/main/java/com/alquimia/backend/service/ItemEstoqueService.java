package com.alquimia.backend.service;

import com.alquimia.backend.dto.response.ItemEstoqueResponseDTO;
import com.alquimia.backend.repository.EstoqueRepository;
import com.alquimia.backend.repository.ItemEstoqueRepository;
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

    public void incrementarItem(Integer cdEstoque, Integer cdProduto, Integer qtde){
        if (qtde < 0) throw new IllegalArgumentException("Quantidade deve ser maior que 0");

        var estoque = estoqueRepository.findByCdEstoque(cdEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Estoque não encontrado"));

        if(!Boolean.TRUE.equals(estoque.getIsAtivo())) throw new IllegalStateException("Estoque inativo");
    }

}
