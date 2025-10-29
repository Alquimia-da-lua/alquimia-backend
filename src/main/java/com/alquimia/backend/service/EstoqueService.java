package com.alquimia.backend.service;


import com.alquimia.backend.dto.response.EstoqueResponseDTO;
import com.alquimia.backend.dto.response.ItemEstoqueResponseDTO;
import com.alquimia.backend.model.Estoque;
import com.alquimia.backend.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    EstoqueRepository estoqueRepository;

    @Transactional
    public EstoqueResponseDTO criarEstoque (boolean isAtivo){
        Estoque novoEstoque = new Estoque();

        novoEstoque.setIsAtivo(isAtivo);
        estoqueRepository.save(novoEstoque);

        return new EstoqueResponseDTO(
                novoEstoque.getCdEstoque(),
                novoEstoque.getIsAtivo(),
                List.of() //sem itens na criacao
        );
    }

    @Transactional(readOnly = true)
    public EstoqueResponseDTO buscarEstoquePorId(Integer cdEstoque){
        var estoque = estoqueRepository.findByCdEstoque(cdEstoque)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado!"));

        List<ItemEstoqueResponseDTO> itensEstoque = estoque.getItens()
                .stream()
                .map(i -> new ItemEstoqueResponseDTO(
                        i.getCdItemEstoque(),
                        i.getCdProduto().getCdProduto(),
                        i.getCdEstoque().getCdEstoque(),
                        i.getQtItemEstoque()
                )).toList();

        return new EstoqueResponseDTO(estoque.getCdEstoque(), estoque.getIsAtivo(), itensEstoque);
    }

    @Transactional(readOnly = true)
    public EstoqueResponseDTO buscarEstoqueAtivoPorId(Integer cdEstoque){
        var estoque = estoqueRepository.findByCdEstoque(cdEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Estoque não encontrado."));

        if (!Boolean.TRUE.equals(estoque.getIsAtivo())) throw new IllegalStateException("Estoque desativado.");

        var itensEstoque = estoque.getItens()
                .stream()
                .map(itemEstoque -> new ItemEstoqueResponseDTO(
                        itemEstoque.getCdItemEstoque(),
                        itemEstoque.getCdProduto().getCdProduto(),
                        itemEstoque.getCdEstoque().getCdEstoque(),
                        itemEstoque.getQtItemEstoque()
                )).toList();

        return new EstoqueResponseDTO(
                estoque.getCdEstoque(),
                estoque.getIsAtivo(),
                itensEstoque
        );
    }

    @Transactional(readOnly = true)
    public List<EstoqueResponseDTO> listarEstoquesAtivos(){
        return estoqueRepository.findAllByIsAtivoTrue()
                .stream()
                .map(e -> new EstoqueResponseDTO(
                        e.getCdEstoque(),
                        e.getIsAtivo(),
                        List.of()
                )).toList();
    }

    @Transactional
    public void desativarEstoque(Integer cdEstoque){
        var estoque = this.estoqueRepository.findByCdEstoque(cdEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Estoque não encontrado"));
        estoque.setIsAtivo(false); //soft delete
    }

    @Transactional
    public void reativarEstoque(Integer cdEstoque){
        var estoque = this.estoqueRepository.findByCdEstoque(cdEstoque)
                .orElseThrow(()-> new IllegalArgumentException("Estoque não encontrado"));

        estoque.setIsAtivo(true);
    }


}
