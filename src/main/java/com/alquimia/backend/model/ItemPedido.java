package com.alquimia.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itemPedido")
public class ItemPedido {

    @Column(nullable = false)
    private Double vlItemPedido;
    @Column(nullable = false)
    private Integer qtItemPedido;

    @ManyToOne
    @JoinColumn(name = "cdPedido")
    private Pedido cdPedido;

    @ManyToOne
    @JoinColumn(name = "cdProduto")
    private Produto cdProduto;


}
