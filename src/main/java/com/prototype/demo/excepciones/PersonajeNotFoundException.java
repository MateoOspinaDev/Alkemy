package com.prototype.demo.excepciones;

import lombok.Data;

@Data
public class PersonajeNotFoundException extends RuntimeException {
    private String code;

    public PersonajeNotFoundException(String code,String message) {
        super(message);
        this.code=code;
    }
}
