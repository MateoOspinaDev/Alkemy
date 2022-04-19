package com.prototype.demo.excepciones;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
public class RequestException extends RuntimeException {

private String code;

    public RequestException(String code,String message) {
        super(message);
        this.code=code;
    }
}
