package com.prototype.demo.service;

import com.prototype.demo.model.Rol;
import com.prototype.demo.model.Usuario;
import com.prototype.demo.repository.RolRepository;
import com.prototype.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IUsuarioServiceImp implements IUsuarioService, UserDetailsService {
    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final RolRepository rolRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByUsername(username);
        if(user == null) {
            //log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            //log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> { //ASignamos los roles del usuario a la coleccion de roles del usuario de SpringSecurity
                authorities.add(new SimpleGrantedAuthority(role.getNombre()));//convertimos cada nombre de rol en un rol de SpSecurity
            });
            //Devolvemos un usuario de userDetails
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    @Override
    public Usuario saveUsuario(Usuario user) {
        //log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usuarioRepository.save(user);
    }

    @Override
    public Rol saveRol(Rol role) {
        //log.info("Saving new role {} to the database", role.getName());
        return rolRepository.save(role);
    }

    @Override
    public void addRolToUsuario(String username, String roleName) {
        //log.info("Adding role {} to user {}", roleName, username);
        Usuario user = usuarioRepository.findByUsername(username);
        Rol role = rolRepository.findByNombre(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Usuario getUsuario(String username) {
        //log.info("Fetching user {}", username);
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> getUsuarios() {
        //log.info("Fetching all users");
        return usuarioRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
}
