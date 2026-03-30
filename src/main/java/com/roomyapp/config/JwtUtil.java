package com.roomyapp.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

/*
Clase que genera un token para ser utlizado en el login y en el registro para poder navegar por la web sin solicitar
contrasenya continuamente y como una forma de garantizar la autenticación del usuario
 */
@Component
public class JwtUtil {

    // Lee la clave de las propiedades o variable de entorno JWT_SECRET
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:604800000}")
    private long expiration;

    private Key key;

    // Inicializar la clave después de inyectar las propiedades
    private void initKey() {
        if (key == null) {
            this.key = Keys.hmacShaKeyFor(
                Base64.getEncoder().encode(secretKey.getBytes())
            );
        }
    }

    public String generateToken(String email, String role){
        initKey();
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para obtener la clave (lo usará el filtro JWT)
    public Key getKey() {
        initKey();
        return key;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
