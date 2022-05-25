package com.prototype.demo.service.Impl;

import com.prototype.demo.model.Genero;
import com.prototype.demo.repository.GeneroRepository;
import com.prototype.demo.service.IGeneroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IGeneroServiceImp implements IGeneroService {
    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public Genero saveSubject(Genero genero) {
        return generoRepository.save(genero);
    }
}
