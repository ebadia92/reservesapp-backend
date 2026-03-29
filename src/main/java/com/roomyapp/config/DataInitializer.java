package com.roomyapp.config;

import com.roomyapp.entity.User;
import com.roomyapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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

            if (userRepository.findByEmail("admin@test.com").isEmpty()) {

                //Creamos usuario Admin
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@test.com");
                admin.setPassword(passwordEncoder.encode("1234"));
                admin.setRole(User.Role.ADMIN);

                userRepository.save(admin);
                System.out.println("Admin creado automáticamente");
            } else{
                System.out.println("Admin ya existe");
            }
        };
    }
}
