package com.alquimia.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(Integer  cdProduto) {
        super(String.format("O produto com o código %d não foi encontrado", cdProduto));
    }
}
