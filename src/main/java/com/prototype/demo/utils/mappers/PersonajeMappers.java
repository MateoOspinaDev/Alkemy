package com.prototype.demo.utils.mappers;

import com.prototype.demo.dtos.PersonajeSinDetallesDto;
import com.prototype.demo.model.Personaje;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonajeMappers {
    private final ModelMapper mapper=new ModelMapper();

    public PersonajeSinDetallesDto PersonajeToPersonajeSinDetallesDto(Personaje personaje) {
        return mapper.map(personaje, PersonajeSinDetallesDto.class);
    }

    public Personaje PersonajeSinDetallesDtoToPersonaje(PersonajeSinDetallesDto personajeSinDetallesDto) {
        return mapper.map(personajeSinDetallesDto, Personaje.class);
    }
}
