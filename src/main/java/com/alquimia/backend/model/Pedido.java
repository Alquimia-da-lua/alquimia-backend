package com.alquimia.backend.model;

import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pedido")
public class Pedido {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdPedido;

    @Column(nullable = false)
    private Double vlPedido;

    @Column(nullable = false)
    private Double vlFrete;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    List<ItemPedido> itens;

    @ManyToOne
    @JoinColumn(name = "cdUsuario")
    private Usuario cdUsuario;

}
