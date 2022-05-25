package com.prototype.demo.service.Impl;

import com.prototype.demo.model.Personaje;
import com.prototype.demo.dtos.PersonajeSinDetallesDto;
import com.prototype.demo.repository.PersonajeRepository;
import com.prototype.demo.service.IPersonajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IPersonajeServiceImp implements IPersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Override
    public List<Personaje> getPersonajes() {
        return personajeRepository.findAll();
    }

    @Override
    public boolean existByNombre(String nombre) {
        return personajeRepository.existsByNombre(nombre);
    }

    @Override
    public boolean existByEdad(int edad) {
        return personajeRepository.existsByEdad(edad);
    }

    @Override
    public Personaje savePersonaje(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    @Override
    public boolean existByPeso(float peso) {
        return personajeRepository.existsByPeso(peso);
    }

    @Override
    public void deletePersonaje(Long id) {

        personajeRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {

        return personajeRepository.existsById(id);
    }

    @Override
    @Transactional
    public Personaje updatePersonaje(Personaje personaje) {
        if (personaje.getId() != null && personajeRepository.existsById(personaje.getId())){
            return personajeRepository.save(personaje);
        }
        return null;
    }

    @Override
    public PersonajeSinDetallesDto getPersonajeByNombre(String nombre) {
        Personaje personaje = personajeRepository.findByNombre(nombre);
        return new PersonajeSinDetallesDto(personaje.getImagen(), personaje.getNombre());
    }

    @Override
    public List<PersonajeSinDetallesDto> getPersonajeByPeso(float peso) {
        List<Personaje> personajes = personajeRepository.findByPeso(peso);
        List<PersonajeSinDetallesDto> personajeSinDetalleDtos = new ArrayList<>();
        personajes.forEach(personaje -> personajeSinDetalleDtos.add(
                new PersonajeSinDetallesDto(personaje.getImagen(), personaje.getNombre())));
        return personajeSinDetalleDtos;
    }

    @Override
    public List<PersonajeSinDetallesDto> GetPersonajeByEdad(int edad) {
        List<Personaje> personajes = personajeRepository.findByEdad(edad);
        List<PersonajeSinDetallesDto> personajeSinDetalleDtos = new ArrayList<>();
        personajes.forEach(personaje -> personajeSinDetalleDtos.add(
                new PersonajeSinDetallesDto(personaje.getImagen(), personaje.getNombre())));
        return personajeSinDetalleDtos;
    }

    @Override
    public List<PersonajeSinDetallesDto> getPersonajesSinDetalles(){

        List<Personaje> personajes = personajeRepository.findAll();
        List<PersonajeSinDetallesDto> personajeSinDetalleDtos = new ArrayList<>();
        personajes.forEach(personaje -> personajeSinDetalleDtos.add(
                new PersonajeSinDetallesDto(personaje.getImagen(), personaje.getNombre())));
        return personajeSinDetalleDtos;
    }


    //********************Por hacer//////*********
    @Override
    public List<PersonajeSinDetallesDto> getPersonajeByIdPelicula(Long idPelicula) {
        List<Personaje> personajes =  personajeRepository.findBypeliculasAsociadasId(idPelicula);
        List<PersonajeSinDetallesDto> personajeSinDetalleDtos = new ArrayList<>();
        personajes.forEach(personaje -> personajeSinDetalleDtos.add(
                new PersonajeSinDetallesDto(personaje.getImagen(), personaje.getNombre())));
        return personajeSinDetalleDtos;
    }
}
