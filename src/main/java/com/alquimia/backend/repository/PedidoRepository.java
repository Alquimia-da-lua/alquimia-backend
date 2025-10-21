package com.alquimia.backend.repository;

import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findByCdPedido (Integer cdPedido);
    List<Pedido> findAllByCdUsuario (Usuario cdUsuario);
}
