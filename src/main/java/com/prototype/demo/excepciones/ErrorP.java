package com.prototype.demo.excepciones;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorP {
    private String code;
    private String message;
}
