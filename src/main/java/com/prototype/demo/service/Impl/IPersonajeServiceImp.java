package com.prototype.demo.service.Impl;

import com.prototype.demo.model.Personaje;
import com.prototype.demo.dtos.PersonajeSinDetallesDto;
import com.prototype.demo.repository.PersonajeRepository;
import com.prototype.demo.service.IPersonajeService;
import com.prototype.demo.utilerias.mappers.PersonajeMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IPersonajeServiceImp implements IPersonajeService {

    private final PersonajeMappers personajeMappers;

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
        return personajeMappers.PersonajeToPersonajeSinDetallesDto(personaje);
    }

    @Override
    public List<PersonajeSinDetallesDto> getPersonajeByPeso(float peso) {
        List<Personaje> personajes = personajeRepository.findByPeso(peso);
        return personajes.stream().map(personajeMappers::PersonajeToPersonajeSinDetallesDto).collect(Collectors.toList());
    }

    @Override
    public List<PersonajeSinDetallesDto> GetPersonajeByEdad(int edad) {
        List<Personaje> personajes = personajeRepository.findByEdad(edad);
        return personajes.stream().map(personajeMappers::PersonajeToPersonajeSinDetallesDto).collect(Collectors.toList());
    }

    @Override
    public List<PersonajeSinDetallesDto> getPersonajesSinDetalles(){
        List<Personaje> personajes = personajeRepository.findAll();
        return personajes.stream().map(personajeMappers::PersonajeToPersonajeSinDetallesDto).collect(Collectors.toList());
    }


    //********************Por hacer//////*********
    @Override
    public List<PersonajeSinDetallesDto> getPersonajeByIdPelicula(Long idPelicula) {
        List<Personaje> personajes =  personajeRepository.findBypeliculasAsociadasId(idPelicula);
        return personajes.stream().map(personajeMappers::PersonajeToPersonajeSinDetallesDto).collect(Collectors.toList());
    }
}
