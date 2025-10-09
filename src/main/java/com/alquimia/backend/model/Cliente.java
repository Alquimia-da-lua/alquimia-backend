package com.alquimia.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    private Integer cdUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cdUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cd_endereco")
    private Endereco cdendereco;
}
