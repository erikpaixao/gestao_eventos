package com.mirante.gestao_eventos.exceptions;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder mensagem = new StringBuilder("");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            mensagem.append(error.getDefaultMessage())
                    .append("; ");
        });

        Map<String, String> erros = Map.of("erro", mensagem.toString());

        return ResponseEntity.badRequest().body(erros);
    }
}