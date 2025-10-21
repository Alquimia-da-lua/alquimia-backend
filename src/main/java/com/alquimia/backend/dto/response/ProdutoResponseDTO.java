package com.alquimia.backend.dto.response;

import com.alquimia.backend.enums.CategoriaEnum;
import com.alquimia.backend.model.Produto;
import com.alquimia.backend.model.Usuario;


public record ProdutoResponseDTO(
        Integer cdProduto,
        String nmProduto,
        String dsProduto,
        Double vlProduto,
        Boolean isAtivo,
        UsuarioResponseDTO cdUsuario,
        CategoriaEnum categoria) {

    public ProdutoResponseDTO(Produto produto){
        this(
                produto.getCdProduto(),
                produto.getNmProduto(),
                produto.getDsProduto(),
                produto.getVlProduto(),
                produto.getIsAtivo(),
                new UsuarioResponseDTO(produto.getCdUsuario()),
                produto.getCategoria()


        );
    }


}
