package com.prototype.demo.service.Impl;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.dtos.PeliculaSinDetallesDto;
import com.prototype.demo.model.Personaje;
import com.prototype.demo.repository.PeliculaRepository;
import com.prototype.demo.repository.PersonajeRepository;
import com.prototype.demo.service.IPeliculaService;
import com.prototype.demo.utils.mappers.PeliculaMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IPeliculaServiceImp implements IPeliculaService {

    private final PeliculaMappers peliculaMappers;

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
    public boolean existById(Long id) {
        return peliculaRepository.existsById(id);
    }

    @Override
    public boolean existByTitulo(String titulo) {
        return peliculaRepository.existsByTitulo(titulo);
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
        if (peliculaRepository.existsById(idPelicula)&&personajeRepository.existsById(idPersonaje)){
                Pelicula pelicula1 = peliculaRepository.findById(idPelicula).get();
                Personaje personaje1 = personajeRepository.findById(idPersonaje).get();
                pelicula1.getPersonajesAsociados().add(personaje1);
        }
    }

    @Override
    public void deletePersonajeToPelicula(Long idPelicula, Long idPersonaje){
        if (peliculaRepository.existsById(idPelicula) && personajeRepository.existsById(idPersonaje)){
                Pelicula pelicula1 = peliculaRepository.findById(idPelicula).get();
                Personaje personaje1 = personajeRepository.findById(idPersonaje).get();
                pelicula1.getPersonajesAsociados().remove(personaje1);
        }
    }

    @Override
    public List<PeliculaSinDetallesDto> getOrderByDate(String order) {
        if(Objects.equals(order, "asc")) {
            List<Pelicula> peliculas = peliculaRepository.findAllOrderByFechaDeCreacionAsc();
            return peliculas.stream().map((peliculaMappers::PeliculaToPeliculaSinDetallesDto)).collect(Collectors.toList());
        }
        else if(Objects.equals(order, "desc")) {
            List<Pelicula> peliculas = peliculaRepository.findAllOrderByFechaDeCreacionDesc();
            return peliculas.stream().map((peliculaMappers::PeliculaToPeliculaSinDetallesDto)).collect(Collectors.toList());
        }
        else return null;
    }


    @Override
    public PeliculaSinDetallesDto getByTitulo(String titulo) {
        Pelicula pelicula = peliculaRepository.findByTitulo(titulo);
        return peliculaMappers.PeliculaToPeliculaSinDetallesDto(pelicula);
    }

    @Override
    public List<PeliculaSinDetallesDto> findByIdGenero(Long idGenero) {
        List<Pelicula> peliculas = peliculaRepository.findByGeneroId(idGenero);
        return peliculas.stream().map((peliculaMappers::PeliculaToPeliculaSinDetallesDto)).collect(Collectors.toList());

    }

    @Override
    public List<PeliculaSinDetallesDto> getPeliculasSinDetalles() {
        List<Pelicula> peliculas = peliculaRepository.findAll();
        return peliculas.stream().map((peliculaMappers::PeliculaToPeliculaSinDetallesDto)).collect(Collectors.toList());

    }

    public List<Pelicula> OrderMovieList(List<Pelicula> peliculas){
        return null;
    }
}
