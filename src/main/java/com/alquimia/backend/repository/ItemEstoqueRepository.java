package com.alquimia.backend.repository;

import com.alquimia.backend.model.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Integer> {
    //buscar por produto
    Optional<ItemEstoque> findByCdProduto(Integer cdProduto);

    //todos os itens dentro de um estoque específico
    List<ItemEstoque> findByCdEstoque_CdEstoque(Integer cdEstoque);

    //itens dentro de um estoque ativo
    List<ItemEstoque> findByCdEstoque_CdEstoqueAndCdEstoque_IsAtivoTrue(Integer cdEstoque);

    //buscar item por estoque e produto
    Optional<ItemEstoque> findByCdEstoque_CdEstoqueAndCdProduto_CdProduto(Integer cdEstoque, Integer cdProduto);
}
