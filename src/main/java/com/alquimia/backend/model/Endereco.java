package com.alquimia.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdEndereco;

    @Column(nullable = false)
    private String nuCep;

    @Column(nullable = false)
    private String dsLogradouro;

    @Column
    private String dsComplemento;

    @Column
    private String dsBairro;

    @Column(nullable = false)
    private String dsLocalidade;

    @Column(nullable = false)
    private String nmEstado;
}
