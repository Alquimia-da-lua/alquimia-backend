package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.EstoqueRequestDTO;
import com.alquimia.backend.dto.response.EstoqueResponseDTO;
import com.alquimia.backend.dto.response.ItemEstoqueResponseDTO;
import com.alquimia.backend.model.Estoque;
import com.alquimia.backend.model.ItemEstoque;
import com.alquimia.backend.repository.EstoqueRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    EstoqueRepository estoqueRepository;

    public EstoqueResponseDTO criarEstoque (EstoqueRequestDTO dto){
        Estoque novoEstoque = new Estoque();

        novoEstoque.setIsAtivo(dto.isAtivo());
        novoEstoque.setItens(new ArrayList<>());

        estoqueRepository.save(novoEstoque);

        List<ItemEstoqueResponseDTO> itensEstoque = novoEstoque.getItens()
                .stream()
                .map(i -> new ItemEstoqueResponseDTO(
                        i.getCdItemEstoque(),
                        i.getCdProduto().getCdProduto(),
                        i.getCdEstoque().getCdEstoque(),
                        i.getQtItemEstoque()
                )).toList();

        return new EstoqueResponseDTO(novoEstoque.getCdEstoque(), novoEstoque.getIsAtivo(), itensEstoque);
    }

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

    public List<ItemEstoqueResponseDTO> itemPedidoAtivo (Integer cdEstoque){
        var itensEstoqueAtivo = estoqueRepository.findAllByCdEstoqueAndIsAtivoTrue(cdEstoque);

        return itensEstoqueAtivo.stream().map(i -> new ItemEstoqueResponseDTO(
                i.getCdItemEstoque(),
                i.getCdProduto().getCdProduto(),
                i.getCdEstoque().getCdEstoque(),
                i.getQtItemEstoque()
        )).toList();
    }




}
