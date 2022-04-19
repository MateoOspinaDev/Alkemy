package com.prototype.demo.excepciones;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PersonajeNotFoundException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;

    public PersonajeNotFoundException(HttpStatus httpStatus,String code,String message) {
        super(message);
        this.httpStatus=httpStatus;
        this.code=code;
    }
}
