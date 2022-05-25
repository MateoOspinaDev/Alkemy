package com.prototype.demo.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Captura cualquier runtime excepcion
public class ControllerAdvice {

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorP> requestExceptionHandler(RequestException ex){
        ErrorP error = ErrorP.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(value = PersonajeNotFoundException.class)
    public ResponseEntity<ErrorP> PersonajeNotFoundExceptionHandler(PersonajeNotFoundException ex){
        ErrorP error = ErrorP.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(value = PeliculaNotFoundException.class)
    public ResponseEntity<ErrorP> PeliculaNotFoundExceptionHandler(PeliculaNotFoundException ex){
        ErrorP error = ErrorP.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

}
