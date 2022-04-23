package com.prototype.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MoviesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesAppApplication.class, args);
	}

	@Bean
		//
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//Test de las funciones de la aplicación
//	@Bean
////Debe ser bean para que springboot lo tome como parte de la app
//	CommandLineRunner run(UsuarioService usuarioService) {
//		return args -> {
//			usuarioService.saveRol(new Rol(null, "ROLE_USER"));
//			usuarioService.saveRol(new Rol(null, "ROLE_MANAGER"));
//			usuarioService.saveRol(new Rol(null, "ROLE_ADMIN"));
//			usuarioService.saveRol(new Rol(null, "ROLE_SUPER_ADMIN"));
//		};
//	}
}
