package com.alquimia.backend.repository;

import com.alquimia.backend.model.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoqueRepository, Integer> {

}
