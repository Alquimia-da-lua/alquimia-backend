package com.alquimia.backend.repository;

import com.alquimia.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByCdProduto(Integer cdProduto);

}
