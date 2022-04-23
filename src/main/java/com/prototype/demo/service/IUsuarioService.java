package com.prototype.demo.service;

import com.prototype.demo.model.Rol;
import com.prototype.demo.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUsuarioService {
    public Usuario saveUsuario(Usuario user);
    public Rol saveRol(Rol role);
    public void addRolToUsuario(String username, String roleName);
    public Usuario getUsuario(String username);
    public List<Usuario> getUsuarios();
    public boolean existsByUsername(String username);
}
