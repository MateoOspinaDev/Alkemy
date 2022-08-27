package com.prototype.demo.utils.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SecurityUtils {
    public String createJWT(User user, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //Algoritmo del jwt que se usará para firmal el token de acceso y de refresco
        String accessToken = com.auth0.jwt.JWT.create()
                .withSubject(user.getUsername()) //El valor de identificacion del usuario que tomará para el token
                .withIssuer(issuer) //Autor del token (URL a la que se envia la peticion)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())) //Asignación de los roles
                .sign(algorithm);
        return accessToken;
    }

    public DecodedJWT getDecodeJWT(String headerToken) {
        String token = headerToken.substring("Bearer ".length());//Eliminamos "Bearer"
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());//El mismo algoritmo de JWT
        JWTVerifier verifier = JWT.require(algorithm).build();//El verificador, que necesita el mismo algoritmo
        DecodedJWT decodedJWT = verifier.verify(token);//Decodificamos el token
        return decodedJWT;//Devolvemos el usuario
    }

    public String getSubjectOfJWT(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        return username;
    }
}
