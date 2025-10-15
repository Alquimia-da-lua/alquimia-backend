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
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "cdUsuario")
public class Cliente extends Usuario{

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @ManyToOne
    @JoinColumn(name = "cdEndereco")
    private Endereco cdEndereco;
}
