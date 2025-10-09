package com.alquimia.backend.model;

import com.alquimia.backend.enums.RoleUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer cdUsuario;

    private String nmUsuario;

    @Column(unique = true)
    private String emailUsuario;

    private String senhaUsuario;

    private RoleUsuario roleUsuario;

    private String nuTelefone;

    private boolean isAtivo;

    private String nuCpf;
}