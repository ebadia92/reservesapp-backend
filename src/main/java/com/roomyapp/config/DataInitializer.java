package com.roomyapp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.roomyapp.repository.UserRepository;

/*
Clase que crea un usuario Admin si no existe.
Esto es provisional hasta hacer migración a BD
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                                PasswordEncoder passwordEncoder){
        return args -> {
        };
    }
}
