package com.alquimia.backend.repository;

import com.alquimia.backend.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {

    Optional<Endereco> findByCdEndereco(Integer cdEndereco);
    Optional<Endereco> findByNuCep(String nuCep);
}
