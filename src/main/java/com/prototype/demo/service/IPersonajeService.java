package com.prototype.demo.service;

import com.prototype.demo.model.Personaje;
import com.prototype.demo.model.PersonajeSinDetalles;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPersonajeService {

    public Personaje savePersonaje(Personaje personaje);
    public void deletePersonaje(Long id);
    public Personaje updatePersonaje(Personaje personaje);
    public boolean existById(Long id);
    public PersonajeSinDetalles getPersonajeByNombre(String nombre);
    public List<PersonajeSinDetalles> getPersonajeByPeso(float peso);
    public List<PersonajeSinDetalles> GetPersonajeByEdad(int edad);
    public List<PersonajeSinDetalles> getPersonajesSinDetalles();
    public List<Personaje> getPersonajes();
    public boolean existByNombre(String nombre);
    public boolean existByEdad(int edad);
    boolean existByPeso(float peso);

    //********************Por hacer//////*********
    public List<Personaje> getPersonajeByIdPelicula(Long idPelicula);
}
