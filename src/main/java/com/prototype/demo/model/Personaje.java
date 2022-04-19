package com.prototype.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Personaje")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Getter
@Setter
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
    private String imagen;
    private String nombre;
    private int edad;
    private float peso;
    private String historia;

    //@JsonIgnore // Ignora las entidades secundarias directamente
    @ManyToMany(mappedBy = "personajesAsociados")//Nombre de la variable que está referenciando en la otra clase
    private Collection<Pelicula> peliculasAsociadas = new ArrayList();



}
