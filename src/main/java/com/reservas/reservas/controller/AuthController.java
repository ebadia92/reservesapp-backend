package com.reservas.reservas.controller;

import com.reservas.reservas.dto.LoginRequest;
import com.reservas.reservas.dto.LoginResponse;
import com.reservas.reservas.entity.User;
import com.reservas.reservas.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    //Constructor con inyección de dependencia de servicio de usuario
    public AuthController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        User user= userService.login(
                request.getEmail(),
                request.getPassword()
        );

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name() //.name() convierte a String y el frontend recibe ADMIN o EMPLOYEE, porque Role es enum
        );

    }
}
