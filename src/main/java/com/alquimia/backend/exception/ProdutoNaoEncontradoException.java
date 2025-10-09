package com.alquimia.backend.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(Integer  cdProduto) {
        super(String.format("O produto com o código %d não foi encontrado", cdProduto));
    }
}
