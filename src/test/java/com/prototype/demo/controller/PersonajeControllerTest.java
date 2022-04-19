package com.prototype.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototype.demo.DatosTest;
import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;
import com.prototype.demo.service.IPersonajeService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }


    @WithMockUser
    @Test
    void shouldGetAllPersonajes() throws Exception {
        when(iPersonajeService.getPersonajes()).thenReturn(DatosTest.listarPersonajes());

        mockMvc.perform(get("/characters").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Mateo"))
                .andExpect(jsonPath("$[1].nombre").value("Adriana"));

        verify(iPersonajeService).getPersonajes();
    }

    @WithMockUser
    @Test
    void shouldSavePersonaje() throws Exception {
        Personaje personaje = new Personaje(1L, "Adriana jpg", "Adriana", 25, 55f,
                "Nacido...", new ArrayList<Pelicula>() {});
        when(iPersonajeService.savePersonaje(any())).thenReturn(personaje);

        mockMvc.perform(post("/characters").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaje)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Adriana"));

        verify(iPersonajeService).savePersonaje(personaje);
    }

    @WithMockUser
    @Test
    void shouldBackBadRequestBecauseBodyContentIsNull() throws Exception {
        mockMvc.perform(post("/characters").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void shouldBackUnsupportedMediaTypeBecauseBodyFormatIsInvalid() throws Exception {
        Personaje personaje = new Personaje(1L, "Adriana jpg", "Adriana", 25, 55f,
                "Nacido...", new ArrayList<Pelicula>() {});

        when(iPersonajeService.savePersonaje(any())).thenReturn(personaje);

        mockMvc.perform(post("/characters").contentType(MediaType.APPLICATION_ATOM_XML)
                        .content(objectMapper.writeValueAsString(personaje)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @WithMockUser
    @Test
    void shouldBackRequestExceptionBecauseOneFieldIsNull() throws Exception {
        Personaje personaje = new Personaje(1L, null, "Adriana", 25, 55f,
                "Nacido...", new ArrayList<Pelicula>() {});
        when(iPersonajeService.savePersonaje(any())).thenReturn(personaje);

        mockMvc.perform(post("/characters").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaje)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ningún dato puede ser nulo"));
    }

    @WithMockUser
    @Test
    void shouldDeletePersonaje() throws Exception {
        when(iPersonajeService.existById(1L)).thenReturn(true);

        mockMvc.perform(delete("/characters/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(iPersonajeService).deletePersonaja(1L);
    }

    @WithMockUser
    @Test
    void shouldReturnNotFoundBecausePersonajeNoExist() throws Exception {
        when(iPersonajeService.existById(1L)).thenReturn(false);

        mockMvc.perform(delete("/characters/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));
    }

    @WithMockUser
    @Test
    void shouldBackPersonajeByAnyValidParameter() throws Exception {
        Personaje personaje = new Personaje(1L, null, "Adriana", 25, 55f,
                "Nacido...", new ArrayList<Pelicula>() {});
        List<Personaje> personajeLista = DatosTest.listarPersonajes();

        when(iPersonajeService.existByNombre(anyString())).thenReturn(true);
        when(iPersonajeService.existByEdad(anyInt())).thenReturn(true);
        when(iPersonajeService.existByPeso(anyFloat())).thenReturn(true);

        when(iPersonajeService.getPersonajeByNombre("Adriana")).thenReturn(personaje);

        when(iPersonajeService.getPersonajeByPeso(55f)).thenReturn(
                personajeLista.stream().filter(personaje1 -> personaje1.getPeso()==55f).collect(Collectors.toList()));

        when(iPersonajeService.GetPersonajeByEdad(25)).thenReturn(
                personajeLista.stream().filter(personaje1 -> personaje1.getEdad()==25).collect(Collectors.toList()));

        mockMvc.perform(get("/characters?name=Adriana").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.nombre").value("Adriana"));

        mockMvc.perform(get("/characters?age=25").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].nombre").value("Adriana"));

        mockMvc.perform(get("/characters?peso=55").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].nombre").value("Adriana"));
    }


    @WithMockUser
    @Test
    void shouldReturnNotFoundBecauseNoExistPersonajeByParameter() throws Exception {

        when(iPersonajeService.existByNombre(anyString())).thenReturn(false);
        when(iPersonajeService.existByEdad(anyInt())).thenReturn(false);
        when(iPersonajeService.existByPeso(anyFloat())).thenReturn(false);

        mockMvc.perform(get("/characters?name=Adriana").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));

        mockMvc.perform(get("/characters?age=25").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));

        mockMvc.perform(get("/characters?peso=55").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));
    }

}