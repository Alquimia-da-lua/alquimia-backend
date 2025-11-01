package com.alquimia.backend.repository;

import com.alquimia.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCdUsuario(Integer cdUsuario);

    // listar todos usuarios ativos
    List<Usuario> findAllByIsAtivoTrue();

    Optional<Usuario> findByEmailUsuario(String emailUsuario);
    Optional<Usuario> findByNuCpf(String nuCpf);
    Optional<Usuario> findByEmailUsuarioAndCdUsuarioNot(String emailUsuario, Integer cdUsuario);
    Optional<Usuario> findByNuCpfAndCdUsuarioNot(String nuCpf, Integer cdUsuario);
}

