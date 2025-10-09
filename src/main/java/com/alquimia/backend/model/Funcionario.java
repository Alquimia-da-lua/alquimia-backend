package com.alquimia.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario{
    @Id
    private Integer cdUsuario;

    private Integer nuMatricula;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cdUsuario")
    private Usuario usuario;
}
