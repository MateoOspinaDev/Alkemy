package com.prototype.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;
@ComponentScan
@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
@Table(name = "Pelicula")
public class Pelicula {

    @SequenceGenerator(
            name = "pelicula_sequence",
            sequenceName = "pelicula_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pelicula_sequence"
    )
    @Column(name = "id")
    private Long id;
    @NonNull
    private String imagen;
    @NonNull
    private String titulo;
    @NonNull
    private Date fechaDeCreacion;
    private float calificacion;

    //@JsonIgnore
    //@JsonIgnoreProperties("peliculasAsociadas")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Genero_id")
    private Genero genero;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY) //Crea una tabla nueva
    @JoinTable(
            name = "Personajes_Asociados", //nombre de la tabla que se creará
            joinColumns = @JoinColumn(name = "personaje_id"), //nombre de la primer columna
            inverseJoinColumns = @JoinColumn(name = "pelicula_id")//Nombre de la otra columna
    )
    private Collection<Personaje> personajesAsociados = new ArrayList();

}
