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
    private Double vlPedido;
    private Double vlFrete;
    private StatusPedido statusPedido;
    private TipoPagamento tipoPagamento;

    List<ItemPedido> itens;

}
