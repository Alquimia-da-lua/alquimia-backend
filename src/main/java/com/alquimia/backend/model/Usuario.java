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
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdUsuario;

    @Column(nullable = false)
    private String nmUsuario;

    @Column(unique = true, nullable = false)
    private String emailUsuario;

    @Column(nullable = false)
    private String senhaUsuario;

    @Enumerated(EnumType.STRING)
    private RoleUsuario roleUsuario;

    @Column(nullable = false)
    private String nuTelefone;

    private boolean isAtivo;

    @Column(unique = true)
    private String nuCpf;
}