package com.prototype.demo.controller;

import com.prototype.demo.excepciones.RequestException;
import com.prototype.demo.model.Usuario;
import com.prototype.demo.service.IUsuarioService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUsuarioService IUsuarioService;

    @Autowired
    JavaMailSender javaMailSender;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario usuario) {
        if(!usuario.getUsername().matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")){
            throw new RequestException(HttpStatus.BAD_REQUEST,"UC-001","Debe ingresar un correo electrónico válido");
        }
        if(IUsuarioService.existsByUsername(usuario.getUsername())){
            throw new RequestException(HttpStatus.BAD_REQUEST,"UC-001","Correo ingresado ya está registrado");
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(usuario.getUsername());
        email.setSubject("Correo de bienvenida");
        email.setText("" +
                "" +
                "Bienvenido a la plataforma de peliculas y series de Disney. Esperamos que su camino con nosotros sea el que esperaba");
        javaMailSender.send(email);

        IUsuarioService.saveUsuario(usuario);
        IUsuarioService.addRolToUsuario(usuario.getUsername(),"ROLE_USER");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/registrar").toUriString());
        return ResponseEntity.created(uri).body(usuario);
    }
}


@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}