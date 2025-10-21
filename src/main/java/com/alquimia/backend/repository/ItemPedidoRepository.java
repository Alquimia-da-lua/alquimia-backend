package com.alquimia.backend.repository;

import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, Integer> {
    Optional<ItemPedido> findAllByCdItemPedido(Integer cdItemPedido);
    List<ItemPedido> findAllByCdPedido(Pedido cdPedido);
}
