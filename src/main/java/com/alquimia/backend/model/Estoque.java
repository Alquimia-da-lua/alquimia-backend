package com.alquimia.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estoque")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estoque {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdEstoque;

    @Column(nullable = false)
    private Boolean isAtivo = true;

    @OneToMany(
            mappedBy = "cdEstoque", //cdEstoque do itemEstoque
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemEstoque> itens = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if (isAtivo==null) isAtivo = true;
    }

    //mantendo lados sincronizados
    public void adicionarItem (ItemEstoque item){
        itens.add(item);
        item.setCdEstoque(this); //referencia o estoque que o item vai pertencer

    }

    public void removerItem (ItemEstoque item){
        itens.remove(item);
        item.setCdEstoque(null); //item passa a nao possuir estoque
    }


}
