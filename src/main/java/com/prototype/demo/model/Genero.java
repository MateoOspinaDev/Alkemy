package com.prototype.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})//Elimina la respuesta de hibernateLazyInitializer en el json
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Genero")
public class Genero {

    @SequenceGenerator(
            name = "genero_sequence",
            sequenceName = "genero_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genero_sequence"
    )
    private Long id;

    @NonNull
    private String imagen;
    @NonNull
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "genero",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    //Si fetchType está en Eager, no permite que las peliculas se elimines
    private Collection<Pelicula> peliculasAsociadas = new ArrayList();


}
