package com.prototype.demo;

import com.prototype.demo.model.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DatosTest {
        //Personajes:

        public static Personaje crearPersonaje001() {
            return (new Personaje(1L, "Mateo jpg", "Mateo", 12, 65f,
                    "Nacido...", new ArrayList<Pelicula>() {
            }));
        }

        public static Personaje crearPersonaje002() {
            return (new Personaje(1L, "Adriana jpg", "Adriana", 25, 55f,
                    "Nacido...", new ArrayList<Pelicula>() {
            }));
        }

        public static Personaje crearPersonaje003() {
            return (new Personaje(1L, "Santiago jpg", "Santiago", 36, 56f,
                    "Nacido...", new ArrayList<Pelicula>() {
            }));
        }

        public static Pelicula crearPelicula() {
            return new Pelicula(1L, "No country for old men jpg", "No country for old men", new Date(1988-06-29),4, crearGenero(),new ArrayList<Personaje>());
        }


        public static ArrayList<Personaje> listarPersonajes(){
            ArrayList<Personaje> personajes = new ArrayList();
            personajes.add(crearPersonaje001());
            personajes.add(crearPersonaje002());
            personajes.add(crearPersonaje003());
            return  personajes;
        }

        public static List<PersonajeSinDetalles> personajeSinDetalles(){
            List<Personaje> personajes = listarPersonajes();
            List<PersonajeSinDetalles> personajeSinDetalles = new ArrayList<>();
            personajes.forEach(personaje -> personajeSinDetalles.add(
                    new PersonajeSinDetalles(personaje.getImagen(), personaje.getNombre())));
            return personajeSinDetalles;
        }



    //Peliculas:

    public static Pelicula crearPelicula001() {
        return (new Pelicula(1L, "Petroleo sangriento jpg", "Petroleo sangriento", new Date(2005-06-20), 5,
                crearGenero(), new ArrayList<Personaje>()));
    }

    public static Pelicula crearPelicula002() {
        return (new Pelicula(2L, "Efecto mariposa jpg", "Efecto mariposa", new Date(2010-06-20), 4,
                crearGenero002(), new ArrayList<Personaje>()));
    }

    public static Pelicula crearPelicula003() {
        return (new Pelicula(3L, "Efecto mariposa 2 jpg", "Efecto mariposa 2", new Date(2015-06-20), 3,
                crearGenero002(), new ArrayList<Personaje>()));
    }




    public static ArrayList<Pelicula> listarPeliculas(){
        ArrayList<Pelicula> peliculas = new ArrayList();
        peliculas.add(crearPelicula001());
        peliculas.add(crearPelicula002());
        peliculas.add(crearPelicula003());
        return  peliculas;
    }

    public static List<PeliculaSinDetalles> listarPeliculasSinDetalles(){
        List<Pelicula> peliculas = listarPeliculas();
        List<PeliculaSinDetalles> peliculaSinDetalles = new ArrayList<>();
        peliculas.forEach(pelicula -> peliculaSinDetalles.add(
                new PeliculaSinDetalles(pelicula.getImagen(), pelicula.getTitulo(),pelicula.getFechaDeCreacion())));
        return peliculaSinDetalles;
    }



    //Genero

    public static Genero crearGenero() {
        return (new Genero(1L, "Drama", "",new ArrayList<Pelicula>()));
    }

    public static Genero crearGenero002() {
        return (new Genero(1L, "fantasia", "",new ArrayList<Pelicula>()));
    }

    //Usuario

    public static Usuario crearUsuario001(){
            return new Usuario(1L, "Mateo Ospina", "mateo.ospina@gmail.com", "123456", new ArrayList<Rol>() {
            });
    }

}

