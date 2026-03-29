package com.roomyapp.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/*
Clase que genera un token para ser utlizado en el login y en el registro para poder navegar por la web sin solicitar
contrasenya continuamente y como una forma de garantizar la autenticación del usuario
 */
@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email, String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                //.setExpiration(new Date(System.currentTimeMillis()+86400000)) duración de un día
                .setExpiration(new Date(System.currentTimeMillis()+604800000)) //token duracion 7 dias
                .signWith(key)
                .compact();
    }
}
