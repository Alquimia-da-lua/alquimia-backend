package com.alquimia.backend.repository;

import com.alquimia.backend.model.Estoque;
import com.alquimia.backend.model.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    //buscar por id (se quiser manter além do findById padrão)
    Optional<Estoque> findByCdEstoque(Integer cdEstoque);

    //existe estoque com id x
    boolean existsByCdEstoque(Integer cdEstoque);

    //lista so os estoques ativos
    List<Estoque> findAllByIsAtivoTrue();

    }