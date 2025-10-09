package com.alquimia.backend.repository;

import com.alquimia.backend.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, Integer> {
    Optional<ItemPedido>cadastrarItemPedido(Integer cdItemPedido, Integer qtItemPedido);
    Optional<ItemPedido>alterarItemPedido(Integer cdItemPedido);
    Optional<ItemPedido>deletarItemPedido(Integer cdItemPedido);
    Optional<ItemPedido>listarItemPedido(Integer cdItemPedido);
}
