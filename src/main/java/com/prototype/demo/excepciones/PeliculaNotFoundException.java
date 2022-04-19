package com.prototype.demo.excepciones;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PeliculaNotFoundException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;

    public PeliculaNotFoundException(HttpStatus httpStatus,String code,String message) {
        super(message);
        this.httpStatus=httpStatus;
        this.code=code;
    }
}
