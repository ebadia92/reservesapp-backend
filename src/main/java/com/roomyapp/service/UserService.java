package com.roomyapp.service;

import com.roomyapp.dto.RegisterRequest;
import com.roomyapp.entity.User;
import com.roomyapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Constructor con inyección de dependencia de Repositorio
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //Login
    public User login(String email, String rawPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        return user;
    }

    //Register
    public User register(String name, String email, String rawPassword){
        //Comprobamos si el usuario existe
        if(userRepository.findByEmail(email).isPresent()){
            throw  new RuntimeException("El usuario ya existe");
        }

        //Crear usuario
        User user =new User();
        user.setName(name);
        user.setEmail(email);

        //Encriptar Contrasenya
        user.setPassword(passwordEncoder.encode(rawPassword));

        //Asignar rol por defecto
        user.setRole(User.Role.EMPLOYEE);

        //guardar en BD
        return userRepository.save(user);
    }
}
