package com.alquimia.backend.exception;

public class CepNaoEncontradoException extends Exception {
    public CepNaoEncontradoException(String cep) {
        super("O cep informado não foi encontrado "+cep);
    }
}
