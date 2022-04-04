package com.prototype.demo.controller;

import com.prototype.demo.model.Pelicula;
import com.prototype.demo.service.IPeliculaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class PeliculaController {

    @Autowired
    private IPeliculaService iPeliculaService;


    @GetMapping
    public ResponseEntity<List<Pelicula>> obtenerPeliculas(){
        return ResponseEntity.ok().body(iPeliculaService.getPeliculas());
    }

    @GetMapping(params = "titulo")
    public ResponseEntity<Pelicula> obtenerPeliculaPorTitulo(@RequestParam("titulo") String titulo){
        return ResponseEntity.ok().body(iPeliculaService.getByTitulo(titulo));
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<Pelicula>> obtenerPeliculaPorGenero(@RequestParam("genre") Long genre){
        return ResponseEntity.ok().body(iPeliculaService.findByIdGenero(genre));
    }

    @GetMapping(params = "order")
    public ResponseEntity<List<Pelicula>> obtenerPeliculasAsc(@RequestParam("order") String order){
        return ResponseEntity.ok().body(iPeliculaService.getOrderByDate(order));
    }

    /*
    @GetMapping(params = "order")
    public ResponseEntity<Personaje> obtenerPersonajePorNombre(@RequestParam("order") String order){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.obtenerPersonajePorNombre(nombre));
    }
*/




    @PostMapping
    public ResponseEntity<Pelicula> guardarPelicula (@RequestBody Pelicula pelicula){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPeliculaService.savePelicula(pelicula));
    }

    @PutMapping
    public ResponseEntity<Pelicula> editarPelicula (@RequestBody Pelicula pelicula){
        iPeliculaService.updatePelicula(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(iPeliculaService.getDetalles(pelicula.getTitulo()));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity borrarPelicula(@PathVariable Long id){
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
