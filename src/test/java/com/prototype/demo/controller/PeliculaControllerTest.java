package com.prototype.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototype.demo.DatosTest;
import com.prototype.demo.model.Pelicula;
import com.prototype.demo.dtos.PeliculaSinDetallesDto;
import com.prototype.demo.model.Personaje;
import com.prototype.demo.service.IPeliculaService;
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

import java.sql.Date;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class PeliculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPeliculaService iPeliculaService;

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
    void shouldGetAllPeliculas() throws Exception {
        when(iPeliculaService.getPeliculas()).thenReturn(DatosTest.listarPeliculas());

        mockMvc.perform(get("/movies/details").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Petroleo sangriento"))
                .andExpect(jsonPath("$[1].titulo").value("Efecto mariposa"));

        verify(iPeliculaService,times(2)).getPeliculas();
    }

    @WithMockUser
    @Test
    void shouldGetAllPeliculasSinDetalles() throws Exception {
        when(iPeliculaService.getPeliculasSinDetalles()).thenReturn(DatosTest.listarPeliculasSinDetalles());

        mockMvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Petroleo sangriento"))
                .andExpect(jsonPath("$[1].titulo").value("Efecto mariposa"));

        verify(iPeliculaService,times(2)).getPeliculasSinDetalles();
    }

    @WithMockUser
    @Test
    void shouldReturnNoContentInPeliculas() throws Exception {
        when(iPeliculaService.getPeliculas()).thenReturn(new ArrayList<Pelicula>());

        mockMvc.perform(get("/movies/details").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(iPeliculaService,times(1)).getPeliculas();
    }

    @WithMockUser
    @Test
    void shouldReturnNoContentInPeliculasSinDetalles() throws Exception {
        when(iPeliculaService.getPeliculasSinDetalles()).thenReturn(new ArrayList<PeliculaSinDetallesDto>());

        mockMvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(iPeliculaService,times(1)).getPeliculasSinDetalles();
    }

    @WithMockUser
    @Test
    void shouldSavePelicula() throws Exception {
        Pelicula pelicula = DatosTest.crearPelicula001();
        when(iPeliculaService.savePelicula(any())).thenReturn(pelicula);

        mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pelicula)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Petroleo sangriento"));

        verify(iPeliculaService).savePelicula(pelicula);
    }

    @WithMockUser
    @Test
    void shouldReturnBadRequestBecauseBodyContentIsNull() throws Exception {
        mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void shouldBackUnsupportedMediaTypeBecauseBodyFormatIsInvalid() throws Exception {
        Pelicula pelicula = DatosTest.crearPelicula001();
        when(iPeliculaService.savePelicula(any())).thenReturn(pelicula);

        mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_ATOM_XML)
                        .content(objectMapper.writeValueAsString(pelicula)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @WithMockUser
    @Test
    void shouldBackRequestExceptionBecauseOneFieldIsNull() throws Exception {
        Pelicula pelicula = new Pelicula(1L, "Petroleo sangriento jpg", null, new Date(2005-06-20), 5,
                DatosTest.crearGenero(), new ArrayList<Personaje>());
        when(iPeliculaService.savePelicula(any())).thenReturn(pelicula);

        mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pelicula)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ningún dato puede ser nulo"));
    }

    @WithMockUser
    @Test
    void shouldDeletePelicula() throws Exception {
        when(iPeliculaService.existById(1L)).thenReturn(true);

        mockMvc.perform(delete("/movies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(iPeliculaService).deletePelicula(1L);
    }

    @WithMockUser
    @Test
    void shouldReturnNoContentInDeleteBecausePeliculaNoExist() throws Exception {
        when(iPeliculaService.existById(1L)).thenReturn(false);

        mockMvc.perform(delete("/movies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("pelicula no existe"));
    }

    @WithMockUser
    @Test
    void shouldReturnPeliculaByTitulo() throws Exception {
        PeliculaSinDetallesDto peliculaSinDetallesDto = new PeliculaSinDetallesDto("Adrianajpg", "Adriana",new Date(25-12-1990));
        when(iPeliculaService.existByTitulo(anyString())).thenReturn(true);
        when(iPeliculaService.getByTitulo("Adriana")).thenReturn(peliculaSinDetallesDto);
        mockMvc.perform(get("/movies?titulo=Adriana").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Adriana"));

    }

    @WithMockUser
    @Test
    void shouldReturnBadRequestInPeliculaByTituloBecauseMovieNoExist() throws Exception {
        PeliculaSinDetallesDto peliculaSinDetallesDto = new PeliculaSinDetallesDto("Adrianajpg", "Adriana",new Date(25-12-1990));
        when(iPeliculaService.existByTitulo(anyString())).thenReturn(false);
        when(iPeliculaService.getByTitulo("Adriana")).thenReturn(peliculaSinDetallesDto);
        mockMvc.perform(get("/movies?titulo=Adriana").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("pelicula no existe o parametro de busqueda incorrecto"));

    }







}