package com.alquimia.backend.repository;

import com.alquimia.backend.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {

    Optional<Endereco> findByCdEndereco(Integer cdEndereco);

    @Query("SELECT c.cdEndereco FROM Cliente c WHERE c.cdUsuario = :cdCliente")
    Optional<Endereco> findByClienteId(@Param("cdCliente") Integer cdCliente);

    Optional<Endereco> findByNuCep(String nuCep);
}
