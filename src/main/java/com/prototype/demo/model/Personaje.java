package com.prototype.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Personaje")
public class Personaje {

    @SequenceGenerator(
            name = "personaje_sequence",
            sequenceName = "personaje_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "personaje_sequence"
    )
    private Long id;
    @NonNull
    private String imagen;
    @NonNull
    private String nombre;
    @NonNull
    private int edad;
    @NonNull
    private float peso;
    private String historia;

    //@JsonIgnore // Ignora las entidades secundarias directamente
    @ManyToMany(mappedBy = "personajesAsociados")//Nombre de la variable que está referenciando en la otra clase
    private Collection<Pelicula> peliculasAsociadas = new ArrayList();



}
