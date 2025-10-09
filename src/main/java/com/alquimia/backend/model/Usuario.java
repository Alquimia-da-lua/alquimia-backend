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
public class Usuario {

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

    @Column(unique = true)
    private String nuCpf;

    @OneToOne(mappedBy = "usuario",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Funcionario funcionario;

    @OneToOne(mappedBy = "usuario",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Cliente cliente;
}