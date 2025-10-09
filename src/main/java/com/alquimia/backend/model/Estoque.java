package com.alquimia.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "estoque")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estoque {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer cdEstoque;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isAtivo;

    private List<ItemEstoque> itens;

}
