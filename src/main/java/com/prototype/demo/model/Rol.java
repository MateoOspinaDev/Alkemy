package com.prototype.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @SequenceGenerator(
            name = "rol_sequence",
            sequenceName = "rol_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rol_sequence"
    )
    private Long id;
    private String nombre;
}
