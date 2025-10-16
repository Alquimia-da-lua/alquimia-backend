package com.alquimia.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioNaoEncontradoException extends RuntimeException{

    public UsuarioNaoEncontradoException(){
        super("Usuário não encontrado");
    }
}
