package com.prototype.demo.service;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;
import com.prototype.demo.repository.PeliculaRepository;
import com.prototype.demo.repository.PersonajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class IPeliculaServiceImp implements IPeliculaService {

    @Autowired
    private final PeliculaRepository peliculaRepository;

    @Autowired
    private final PersonajeRepository personajeRepository;


    @Override
    public Pelicula savePelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    @Override
    public void deletePelicula(Long id) {
        peliculaRepository.deleteById(id);
    }

    @Override
    public List<Pelicula> getPeliculas(){

        return peliculaRepository.findAll();

    }

    @Override
    @Transactional
    public Pelicula updatePelicula(Pelicula pelicula) {
        if (pelicula.getId() != null && peliculaRepository.existsById(pelicula.getId())) {

            peliculaRepository.updatePelicula(pelicula.getCalificacion(), pelicula.getFechaDeCreacion(), pelicula.getImagen(),
                    pelicula.getTitulo(), pelicula.getGenero().getId(), pelicula.getId());
        }
    return null;
    }


    @Override
    public Pelicula getDetalles(String nombre) {
        return peliculaRepository.findByTitulo(nombre);
    }

    @Override
    public void addPersonajeToPelicula(Long idPelicula, Long idPersonaje) {
        //if (peliculaRepository.existByTitulo(pelicula.getTitulo())){
        if (peliculaRepository.existsById(idPelicula)){
            if (personajeRepository.existsById(idPersonaje)){
                Pelicula pelicula1 = peliculaRepository.findById(idPelicula).get();
                Personaje personaje1 = personajeRepository.findById(idPersonaje).get();
                pelicula1.getPersonajesAsociados().add(personaje1);
            }
        }
    }

    @Override
    public void deletePersonajeToPelicula(Long idPelicula, Long idPersonaje){
        if (peliculaRepository.existsById(idPelicula)){
            if (personajeRepository.existsById(idPersonaje)){
                Pelicula pelicula1 = peliculaRepository.findById(idPelicula).get();
                Personaje personaje1 = personajeRepository.findById(idPersonaje).get();
                pelicula1.getPersonajesAsociados().remove(personaje1);
            }
        }

    }

    @Override
    public boolean existById(Long id) {
        return peliculaRepository.existsById(id);
    }

    @Override
    public List<Pelicula> getOrderByDate(String order) {
        if(Objects.equals(order, "asc")) return peliculaRepository.findAllOrderByFechaDeCreacionAsc();
        else if(Objects.equals(order, "desc")) return peliculaRepository.findAllOrderByFechaDeCreacionDesc();
        else return null;
    }


    @Override
    public Pelicula getByTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo);
    }

    @Override
    public List<Pelicula> findByIdGenero(Long idGenero) {
        List<Pelicula> peliculas = peliculaRepository.findByGeneroId(idGenero);
        return  peliculas;
    }

    public List<Pelicula> OrderMovieList(List<Pelicula> peliculas){
        return null;
    }
}
