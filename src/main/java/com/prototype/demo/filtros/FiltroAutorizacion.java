package com.prototype.demo.filtros;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class FiltroAutorizacion extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/auth/registrar")) {
            filterChain.doFilter(request, response);
        }

        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);//Tomamos el Authorizathion del header
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { //Si no es nulo y empieza con Bearer
                try {

                    String token = authorizationHeader.substring("Bearer ".length());//Eliminamos "Bearer"
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());//El mismo algoritmo de JWT
                    JWTVerifier verifier = JWT.require(algorithm).build();//El verificador, que necesita el mismo algoritmo
                    DecodedJWT decodedJWT = verifier.verify(token);//Decodificamos el token
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);//
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();   //
                    stream(roles).forEach(role -> {                                       //Obtenemos los roles y los convertimos
                        authorities.add(new SimpleGrantedAuthority(role));                //a rol de SpringSecurity
                    });

                    UsernamePasswordAuthenticationToken authenticationToken =//Token de autenticacion
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);//La peticion sigue su curso
                }catch (Exception exception) {
                    log.error("Error logging in: {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());//Envia el codigo y estatus del error

                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}

