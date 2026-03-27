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

    //Registro que inyecta nombre e email del DTO RegisterRequest
    public User register(RegisterRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        //Encriptación de la contraseña
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //Rol por defecto
        user.setRole(User.Role.EMPLOYEE);

        return userRepository.save(user);
    }
}
