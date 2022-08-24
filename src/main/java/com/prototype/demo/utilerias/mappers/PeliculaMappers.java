package com.prototype.demo.utilerias.mappers;

import com.prototype.demo.dtos.PeliculaSinDetallesDto;
import com.prototype.demo.model.Pelicula;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeliculaMappers {
    private final ModelMapper mapper=new ModelMapper();

    public PeliculaSinDetallesDto PeliculaToPeliculaSinDetallesDto(Pelicula pelicula) {
        return mapper.map(pelicula, PeliculaSinDetallesDto.class);
    }

    public Pelicula PeliculaSinDetallesDtoToPelicula(PeliculaSinDetallesDto peliculaSinDetallesDto) {
        return mapper.map(peliculaSinDetallesDto, Pelicula.class);
    }
}
