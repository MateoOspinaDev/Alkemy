package com.prototype.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototype.demo.DatosTest;
import com.prototype.demo.service.IPersonajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import  static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import  static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class PersonajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonajeService iPersonajeService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @WithMockUser
    @Test
    void obtenerPersonajes() throws Exception {
        when(iPersonajeService.getPersonajes()).thenReturn(DatosTest.listarPersonajes());

        mockMvc.perform(MockMvcRequestBuilders.get("/characters").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].nombre").value("Mateo"));
    }

    @Test
    void obtenerPersonajePorNombre() {
    }

    @Test
    void obtenerPersonajesPorEdad() {
    }

    @Test
    void obtenerPersonajesPorpeso() {
    }

    @Test
    void obtenerPersonajesPorIdMovie() {
    }

    @Test
    void guardarPersonaje() {
    }

    @Test
    void editarPersonaje() {
    }

    @Test
    void borrarPersonaje() {
    }
}