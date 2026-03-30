package com.roomyapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de Comfiguración que se encarga de encriptar la contraseña
 * Permitirá endpoints pubicos
 * Definirá reglas de acceso
 */

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    // Configuración de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {

        http
                .cors(Customizer.withDefaults()) //implementacion
                .csrf(csrf -> csrf.disable()) // necesario para Postman
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones, solo JWT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() //  ESTA ES LA CLAVE
                        .requestMatchers("/auth/register").permitAll() //  ESTA ES LA CLAVE
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // Agregar filtro JWT
                .httpBasic((httpBasic -> httpBasic.disable())); //evitar login basico

        return http.build();
    }
}
