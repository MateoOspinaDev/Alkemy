package com.prototype.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototype.demo.DatosTest;
import com.prototype.demo.model.Pelicula;
import com.prototype.demo.model.Personaje;
import com.prototype.demo.model.PersonajeSinDetalles;
import com.prototype.demo.service.IPersonajeService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import  static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        mockMvc.perform(get("/characters/details").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Mateo"))
                .andExpect(jsonPath("$[1].nombre").value("Adriana"));

        verify(iPersonajeService,times(2)).getPersonajes();
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

        verify(iPersonajeService).deletePersonaje(1L);
    }

    @WithMockUser
    @Test
    void shouldReturnNoContentBecausePersonajeNoExist() throws Exception {
        when(iPersonajeService.existById(1L)).thenReturn(false);

        mockMvc.perform(delete("/characters/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));
    }

    @WithMockUser
    @Test
    void shouldBackPersonajeByAnyValidParameter() throws Exception {
        PersonajeSinDetalles personaje = new PersonajeSinDetalles("Adrianajpg", "Adriana");
        List<PersonajeSinDetalles> personajeLista = DatosTest.personajeSinDetalles();

        when(iPersonajeService.existByNombre(anyString())).thenReturn(true);
        when(iPersonajeService.existByEdad(anyInt())).thenReturn(true);
        when(iPersonajeService.existByPeso(anyFloat())).thenReturn(true);

        when(iPersonajeService.getPersonajeByNombre("Adriana")).thenReturn(personaje);

        when(iPersonajeService.getPersonajeByPeso(55f)).thenReturn(personajeLista);

        when(iPersonajeService.GetPersonajeByEdad(25)).thenReturn(personajeLista);

        mockMvc.perform(get("/characters?name=Adriana").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Adriana"));

        mockMvc.perform(get("/characters?age=25").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Mateo"));

        mockMvc.perform(get("/characters?peso=55").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Mateo"));
    }


    @WithMockUser
    @Test
    void shouldReturnNotFoundBecauseNoExistPersonajeByParameter() throws Exception {

        when(iPersonajeService.existByNombre(anyString())).thenReturn(false);
        when(iPersonajeService.existByEdad(anyInt())).thenReturn(false);
        when(iPersonajeService.existByPeso(anyFloat())).thenReturn(false);

        mockMvc.perform(get("/characters?name=Adriana").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));

        mockMvc.perform(get("/characters?age=25").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));

        mockMvc.perform(get("/characters?peso=55").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("personaje no existe o parametro de busqueda incorrecto"));
    }

}