package com.roomyapp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Filtro que valida el token JWT en cada solicitud
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extraer el token del header Authorization
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                logger.debug("Token recibido: " + token.substring(0, Math.min(20, token.length())) + "...");

                try {
                    // Validar y obtener los claims del token
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(jwtUtil.getKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    String email = claims.getSubject();
                    String role = (String) claims.get("role");

                    logger.debug("Token válido para usuario: " + email);

                    // Crear autoridades basadas en el rol
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    if (role != null) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    }

                    // Crear el token de autenticación
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);

                    // Establecer en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception e) {
                    logger.error("Error al validar token JWT: " + e.getMessage());
                    SecurityContextHolder.clearContext();
                }
            } else {
                logger.debug("No se encontró token en el header Authorization");
            }
        } catch (Exception e) {
            logger.error("Error en JwtAuthenticationFilter: " + e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
