package com.prototype.demo.controller;

import com.prototype.demo.model.Genero;
import com.prototype.demo.service.IGeneroService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/genre")
public class GeneroController {

    @Autowired
    private IGeneroService iGeneroService;

    @PostMapping
    public ResponseEntity<Genero> guardarGenero(@RequestBody Genero genero){
        return ResponseEntity.status(HttpStatus.CREATED).body(iGeneroService.saveSubject(genero));
    }
}
