package com.prototype.demo.controller;

import com.prototype.demo.model.Personaje;
import com.prototype.demo.service.IPersonajeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@AllArgsConstructor
public class PersonajeController {

    @Autowired
    private IPersonajeService iPersonajeService;

    @GetMapping
    public ResponseEntity<List<Personaje>> obtenerPersonajes(){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.getPersonajes());
    }

    @GetMapping(params = "name")
    public ResponseEntity<Personaje> obtenerPersonajePorNombre(@RequestParam("name") String name){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.getPersonajeByNombre(name));
    }


    @GetMapping(params = "age")
    public ResponseEntity<List<Personaje>> obtenerPersonajesPorEdad(@RequestParam("age") int age){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.GetPersonajeByEdad(age));
    }

    @GetMapping(params = "peso")
    public ResponseEntity<List<Personaje>> obtenerPersonajesPorpeso(@RequestParam("peso") float peso){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.getPersonajeByPeso(peso));
    }


    ///************Por hacer/////********
    @GetMapping(params = "movies")
    public ResponseEntity<List<Personaje>> obtenerPersonajesPorIdMovie(@RequestParam("movies") Long movies){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.getPersonajes());
    }



    @PostMapping
    public ResponseEntity<Personaje> guardarPersonaje (@RequestBody Personaje personaje){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.savePersonaje(personaje));
    }




    @PutMapping
    public ResponseEntity<Personaje> editarPersonaje ( @RequestBody Personaje personaje){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.updatePersonaje(personaje));
    }



    @DeleteMapping(value = "/{id}")
    public ResponseEntity borrarPersonaje(@PathVariable ("id") Long id){
        iPersonajeService.deletePersonaja(id);
        return ResponseEntity.ok(!iPersonajeService.existById(id));
    }

}
