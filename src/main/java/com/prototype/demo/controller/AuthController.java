package com.prototype.demo.controller;

import com.prototype.demo.model.Usuario;
import com.prototype.demo.service.UsuarioServiceImp;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor @RestController @RequestMapping("/auth")
public class AuthController {
    @Autowired
    private final UsuarioServiceImp usuarioServiceImp;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario usuario) {
        usuarioServiceImp.saveUsuario(usuario);
        usuarioServiceImp.addRolToUsuario(usuario.getUsername(),"ROLE_USER");
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/registrar").toUriString());
        return ResponseEntity.created(uri).body(usuario);
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}