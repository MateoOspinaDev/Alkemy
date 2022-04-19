package com.prototype.demo.service;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.PeliculaSinDetalles;
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
    public boolean existByTitulo(String titulo);
    public List<PeliculaSinDetalles> getOrderByDate(String order);
    public PeliculaSinDetalles getByTitulo(String titulo);
    List<PeliculaSinDetalles> findByIdGenero(Long idGenero);
    List<PeliculaSinDetalles> getPeliculasSinDetalles();

}
