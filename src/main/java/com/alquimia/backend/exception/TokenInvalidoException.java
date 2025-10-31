package com.alquimia.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenInvalidoException extends RuntimeException {

    public TokenInvalidoException() {
        super("Token inválido ou expirado");
    }
}
