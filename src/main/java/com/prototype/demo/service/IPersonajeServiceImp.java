package com.prototype.demo.service;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;
import com.prototype.demo.repository.PersonajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IPersonajeServiceImp implements IPersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Override
    public List<Personaje> getPersonajes(){
        return personajeRepository.findAll();
    }

    @Override
    public Personaje savePersonaje(Personaje personaje) {

        return personajeRepository.save(personaje);
    }

    @Override
    public void deletePersonaja(Long id) {
        personajeRepository.deleteById(id);
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
    public Personaje getDetalles(String nombre) {
        return null;
    }

    @Override
    public boolean existById(Long id) {
        return personajeRepository.existsById(id);
    }

    @Override
    public Personaje getPersonajeByNombre(String nombre) {
        return personajeRepository.findByNombre(nombre);
    }

    @Override
    public List<Personaje> getPersonajeByPeso(float peso) {
        return personajeRepository.findByPeso(peso);
    }

    @Override
    public List<Personaje> GetPersonajeByEdad(int edad) {
        return personajeRepository.findByEdad(edad);
    }



    //********************Por hacer//////*********
    @Override
    public List<Personaje> getPersonajeByIdPelicula(Long idPelicula) {
        return null;
    }
}
