package com.prototype.demo.dtos;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaSinDetallesDto {
    private String imagen;
    private String titulo;
    private Date fechaDeCreacion;
}
