package com.alquimia.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "cdUsuario")
public class Funcionario extends Usuario{

    private Integer nuMatricula;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_funcionario", referencedColumnName = "cdUsuario")
    private List<Produto> produtos;
}
