package com.alquimia.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "item_estoque")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEstoque {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdItemEstoque;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) //fetch so busca do banco quando acessar o relacionamento
    @JoinColumn(name = "cd_produto", nullable = false)
    private Produto cdProduto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cd_estoque", nullable = false)
    private Estoque cdEstoque; //mapped by do estoque

    @Min(0)
    @Column(nullable = false)
    private Integer qtItemEstoque = 0;

    public void aumentarQtdeItemEstoque(int qtde){
        if (qtde < 0) throw new IllegalArgumentException("Quantidade negativa!");

        this.qtItemEstoque += qtde;
    }

    public void reduzirQtdeItemEstoque(int qtde){
        if (qtde < 0) throw new IllegalArgumentException("Quantidade negativa.");

        int restante = this.qtItemEstoque - qtde;
        if (restante < 0) throw new IllegalArgumentException("estoque insuficiente.");

        this.qtItemEstoque = restante;
    }

}
