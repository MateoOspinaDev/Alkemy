package com.prototype.demo.service;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPeliculaService {
    public Pelicula savePelicula(Pelicula subject);
    public void deletePelicula(Long id);
    public Pelicula updatePelicula(Pelicula subject);
    List<Pelicula> getPeliculas();
    public Pelicula getDetalles(String nombre);
    public void addPersonajeToPelicula(Long idPersonaje, Long idPelicula);
    public void deletePersonajeToPelicula(Long idPersonaje, Long idPelicula);
    public boolean existById(Long id);
    public List<Pelicula> getOrderByDate(String order);
    public Pelicula getByTitulo(String titulo);
    List<Pelicula> findByIdGenero(Long idGenero);

}
