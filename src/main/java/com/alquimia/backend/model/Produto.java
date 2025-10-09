package com.alquimia.backend.model;

import com.alquimia.backend.dto.request.ProdutoRequestDTO;
import com.alquimia.backend.enums.CategoriaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer cdProduto;

        @Column(nullable = false)
        private String nmProduto;

        @Column(columnDefinition = "TEXT")
        private String dsProduto;

        @Column(nullable = false)
        private Double vlProduto;

        @Column(columnDefinition = "boolean default true")
        private Boolean isAtivo;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "cd_usuario", referencedColumnName = "cdUsuario", nullable = false)
        private Funcionario cdFuncionario;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private CategoriaEnum categoria;

        public void marcarComoDeletado(){
                this.isAtivo = false;
        }

}
