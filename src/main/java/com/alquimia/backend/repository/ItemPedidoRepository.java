package com.alquimia.backend.repository;

import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, Integer> {
    Optional<ItemPedido>cadastrarItemPedido(Integer cdItemPedido, Integer qtItemPedido);
    Optional<ItemPedido>alterarItemPedido(Integer cdItemPedido);
    Optional<ItemPedido>deletarItemPedido(Integer cdItemPedido);
    Optional<ItemPedido>listarItemPedido(Integer cdItemPedido);
    Optional<ItemPedido> findAllByCdItemPedido(Integer cdItemPedido);
    Optional<ItemPedido> findAllByCdPedido(Integer cdPedido);
    List<ItemPedido> findAllByCdPedido(Pedido cdPedido);
}
