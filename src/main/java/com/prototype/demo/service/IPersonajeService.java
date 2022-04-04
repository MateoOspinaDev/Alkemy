package com.prototype.demo.service;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;

import java.util.List;

public interface IPersonajeService {
    public Personaje savePersonaje(Personaje personaje);
    public void deletePersonaja(Long id);
    public Personaje updatePersonaje(Personaje personaje);
    public Personaje getDetalles(String nombre);
    public boolean existById(Long id);
    public Personaje getPersonajeByNombre(String nombre);
    public List<Personaje> getPersonajeByPeso(float peso);
    public List<Personaje> GetPersonajeByEdad(int edad);
    public List<Personaje> getPersonajes();

    //********************Por hacer//////*********
    public List<Personaje> getPersonajeByIdPelicula(Long idPelicula);
}
