package com.alquimia.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DadoDuplicadoException.class)
    private ResponseEntity<?> DadoDuplicadoHandler(DadoDuplicadoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    private ResponseEntity<?> UsuarioNaoEncontradoHandler(UsuarioNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    private ResponseEntity<?> EnderecoNaoEncontradoHandler(EnderecoNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(CepNaoEncontradoException.class)
    private ResponseEntity<?> CepNaoEncontradoHandler(CepNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    private ResponseEntity<?> ProdutoNaoEncontradoHandler(ProdutoNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    private ResponseEntity<?> TokenInvalidoHandler(TokenInvalidoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());
        return ResponseEntity.status(401).body(response);
    }
}
