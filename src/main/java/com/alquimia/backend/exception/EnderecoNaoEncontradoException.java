package com.alquimia.backend.exception;

public class EnderecoNaoEncontradoException extends RuntimeException {
    public EnderecoNaoEncontradoException(Integer cdEndereco) {

        super(String.format("O endereco com o código %d não foi encontrado", cdEndereco));
    }
}
