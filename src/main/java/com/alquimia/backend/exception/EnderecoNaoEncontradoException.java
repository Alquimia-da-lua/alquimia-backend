package com.alquimia.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EnderecoNaoEncontradoException extends RuntimeException {
    public EnderecoNaoEncontradoException(Integer cdEndereco) {

        super(String.format("O endereco com o código %d não foi encontrado", cdEndereco));
    }
}
