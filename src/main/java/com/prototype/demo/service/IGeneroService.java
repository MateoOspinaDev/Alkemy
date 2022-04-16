package com.prototype.demo.service;

import com.prototype.demo.model.Genero;
import org.springframework.stereotype.Service;

@Service
public interface IGeneroService {
    public Genero saveSubject(Genero subject);
}
