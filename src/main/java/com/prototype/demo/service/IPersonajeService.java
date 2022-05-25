package com.prototype.demo.service;

import com.prototype.demo.model.Personaje;
import com.prototype.demo.dtos.PersonajeSinDetallesDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPersonajeService {

    Personaje savePersonaje(Personaje personaje);
    void deletePersonaje(Long id);
    Personaje updatePersonaje(Personaje personaje);
    boolean existById(Long id);
    PersonajeSinDetallesDto getPersonajeByNombre(String nombre);
    List<PersonajeSinDetallesDto> getPersonajeByPeso(float peso);
    List<PersonajeSinDetallesDto> GetPersonajeByEdad(int edad);
    List<PersonajeSinDetallesDto> getPersonajesSinDetalles();
    List<Personaje> getPersonajes();
    boolean existByNombre(String nombre);
    boolean existByEdad(int edad);
    boolean existByPeso(float peso);

    //********************Por hacer//////*********
    public List<PersonajeSinDetallesDto> getPersonajeByIdPelicula(Long idPelicula);
}
