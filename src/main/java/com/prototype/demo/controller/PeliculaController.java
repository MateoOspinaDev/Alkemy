package com.prototype.demo.controller;

import com.prototype.demo.excepciones.PeliculaNotFoundException;
import com.prototype.demo.excepciones.PersonajeNotFoundException;
import com.prototype.demo.excepciones.RequestException;
import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.PeliculaSinDetalles;
import com.prototype.demo.service.IPeliculaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class PeliculaController {

    @Autowired
    private IPeliculaService iPeliculaService;


    @GetMapping("/details")
    public ResponseEntity<List<Pelicula>> obtenerPeliculas(){
        if(iPeliculaService.getPeliculas().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(iPeliculaService.getPeliculas());
    }

    @GetMapping
    public ResponseEntity<List<PeliculaSinDetalles>> obtenerPeliculasSinDetalles(){
        if(iPeliculaService.getPeliculasSinDetalles().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok().body(iPeliculaService.getPeliculasSinDetalles());
    }

    @GetMapping(params = "titulo")
    public ResponseEntity<PeliculaSinDetalles> obtenerPeliculaPorTitulo(@RequestParam("titulo") String titulo){
        if(!iPeliculaService.existByTitulo(titulo)){
            throw new PeliculaNotFoundException(HttpStatus.BAD_REQUEST,"EC-003","pelicula no existe o parametro de busqueda incorrecto");
        }
        return ResponseEntity.ok().body(iPeliculaService.getByTitulo(titulo));
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<PeliculaSinDetalles>> obtenerPeliculaPorGenero(@RequestParam("genre") Long genre){
        return ResponseEntity.ok().body(iPeliculaService.findByIdGenero(genre));
    }

    @GetMapping(params = "order")
    public ResponseEntity<List<PeliculaSinDetalles>> obtenerPeliculasAsc(@RequestParam("order") String order){
        if(!Objects.equals(order, "asc") && !Objects.equals(order, "desc")){
            throw new RequestException(HttpStatus.BAD_REQUEST,"EM-001","Orden ingresado no es valido");
        }
        return ResponseEntity.ok().body(iPeliculaService.getOrderByDate(order));
    }


    @PostMapping
    public ResponseEntity<Pelicula> guardarPelicula (@RequestBody Pelicula pelicula){
        if(pelicula.getFechaDeCreacion()==null || pelicula.getTitulo()==null|| pelicula.getImagen()==null|| pelicula.getGenero()==null)
        {
            throw new RequestException(HttpStatus.BAD_REQUEST,"EC-002","ningún dato puede ser nulo");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iPeliculaService.savePelicula(pelicula));
    }

    @PutMapping
    public ResponseEntity<Pelicula> editarPelicula (@RequestBody Pelicula pelicula){
        if(!iPeliculaService.existById(pelicula.getId())){
            throw new PeliculaNotFoundException(HttpStatus.BAD_REQUEST,"EC-003","pelicula a editar no existe");
        }
        iPeliculaService.updatePelicula(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(iPeliculaService.getDetalles(pelicula.getTitulo()));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity borrarPelicula(@PathVariable Long id){
        if(!iPeliculaService.existById(id)){
            throw new PeliculaNotFoundException(HttpStatus.BAD_REQUEST,"EC-003","pelicula  no existe");
        }
        iPeliculaService.deletePelicula(id);
        return ResponseEntity.ok(!iPeliculaService.existById(id));
    }

    @PostMapping(value="/{idPelicula}/characters/{idPersonaje}")
    public ResponseEntity<?> agregarPersonajeAPelicula(@PathVariable Long idPelicula,@PathVariable Long idPersonaje){
        iPeliculaService.addPersonajeToPelicula(idPelicula,idPersonaje);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/{idPelicula}/characters/{idPersonaje}")
    public ResponseEntity<?> borrarPersonajeDePelicula(@PathVariable Long idPelicula,@PathVariable Long idPersonaje){
        iPeliculaService.deletePersonajeToPelicula(idPelicula,idPersonaje);
        return ResponseEntity.ok().build();
    }


}
