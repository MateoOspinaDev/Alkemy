package com.prototype.demo.service;

import com.prototype.demo.model.Personaje;
import com.prototype.demo.model.PersonajeSinDetalles;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPersonajeService {

    Personaje savePersonaje(Personaje personaje);
    void deletePersonaje(Long id);
    Personaje updatePersonaje(Personaje personaje);
    boolean existById(Long id);
    PersonajeSinDetalles getPersonajeByNombre(String nombre);
    List<PersonajeSinDetalles> getPersonajeByPeso(float peso);
    List<PersonajeSinDetalles> GetPersonajeByEdad(int edad);
    List<PersonajeSinDetalles> getPersonajesSinDetalles();
    List<Personaje> getPersonajes();
    boolean existByNombre(String nombre);
    boolean existByEdad(int edad);
    boolean existByPeso(float peso);

    //********************Por hacer//////*********
    public List<PersonajeSinDetalles> getPersonajeByIdPelicula(Long idPelicula);
}
