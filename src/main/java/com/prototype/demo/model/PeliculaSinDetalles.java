package com.prototype.demo.model;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaSinDetalles {
    private String imagen;
    private String titulo;
    private Date fechaDeCreacion;
}
