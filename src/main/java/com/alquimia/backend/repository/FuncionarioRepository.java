package com.alquimia.backend.repository;

import com.alquimia.backend.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByNuMatricula(int nuMatricula);
}
