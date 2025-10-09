package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.CategoriaEnum;
import com.alquimia.backend.model.Funcionario;
import com.alquimia.backend.model.Produto;


public record ProdutoResponseDTO(
        Integer cdProduto,
        String nmProduto,
        String dsProduto,
        Double vlProduto,
        Boolean isAtivo,
        Funcionario cdFuncionario,
        CategoriaEnum categoria) {

    public ProdutoResponseDTO(Produto produto){
        this(
                produto.getCdProduto(),
                produto.getNmProduto(),
                produto.getDsProduto(),
                produto.getVlProduto(),
                produto.getIsAtivo(),
                produto.getCategoria(),
                new FuncionarioDTO(produto.getCdFuncionario())

        );
    }


}
