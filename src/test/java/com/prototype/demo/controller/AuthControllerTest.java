package com.prototype.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototype.demo.DatosTest;
import com.prototype.demo.service.IUsuarioService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender mailSender;


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


    @Test
    void shouldSaveUser() throws Exception {
        //when(usuarioService.saveUsuario(any())).thenReturn(DatosTest.crearUsuario001());

        mockMvc.perform(post("/auth/registrar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(DatosTest.crearUsuario001())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(DatosTest.crearUsuario001().getUsername()));
    }
    @Test
    public void test() throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("448099205@qq.com", "db1995");
        helper.setSubject("Title");
        helper.setText("<h1 style='color: #654321;text-align: center;'>H1 Tag</h1><p>Some text...</p>", true);
        helper.setTo("7104438@qq.com");
        mailSender.send(message);
    }
}