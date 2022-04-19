package com.prototype.demo.service;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
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
    public boolean existByNombre(String nombre);
    public boolean existByEdad(int edad);

    //********************Por hacer//////*********
    public List<Personaje> getPersonajeByIdPelicula(Long idPelicula);

    boolean existByPeso(float peso);
}
