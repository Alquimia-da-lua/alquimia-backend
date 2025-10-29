package com.alquimia.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itemPedido")
@Entity
public class ItemPedido {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdItemPedido;

    @Column(nullable = false)
    private Double vlItemPedido;

    @Column(nullable = false)
    private Integer qtItemPedido;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cdPedido", nullable = false)
    private Pedido cdPedido;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cdProduto", nullable = false)
    private Produto cdProduto;
}
