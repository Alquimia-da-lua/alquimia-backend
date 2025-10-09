package com.alquimia.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "item_estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEstoque {

    @ManyToOne
    @JoinColumn(name = "cd_produto", nullable = false)
    @Column(nullable = false)
    private Produto cdProduto;

    @ManyToOne
    @JoinColumn(name = "cd_estoque")
    @Column(nullable = false)
    private Estoque cdEstoque;

    @Column(nullable = false)
    private Integer qtItemEstoque;
}
