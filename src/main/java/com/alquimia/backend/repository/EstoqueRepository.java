package com.alquimia.backend.repository;

import com.alquimia.backend.model.Estoque;
import com.alquimia.backend.model.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    Optional<Estoque> findByCdEstoque (Integer cdEstoque);
    Boolean existsByCdEstoque(Integer cdEstoque);
    List<ItemEstoque> findAllByIsAtivoTrue();
    List<ItemEstoque> findAllByCdEstoqueAndIsAtivoTrue(Integer cdEstoque);
}