package com.alquimia.backend.repository;

import com.alquimia.backend.enums.CategoriaEnum;
import com.alquimia.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Optional<Produto> findByCdProduto(Integer cdProduto);

    Optional<Produto> findDistinctByCategoria(CategoriaEnum categoria);

    List<Produto> findAllByIsAtivoTrue();

}
