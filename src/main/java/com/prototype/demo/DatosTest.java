package com.prototype.demo;

import com.prototype.demo.model.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        public static Genero crearGenero() {
            return (new Genero(1L, "Terror", "",new ArrayList<Pelicula>()));
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
    }

