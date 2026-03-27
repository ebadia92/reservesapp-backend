package com.roomyapp.controller;

import com.roomyapp.dto.LoginRequest;
import com.roomyapp.dto.LoginResponse;
import com.roomyapp.dto.RegisterRequest;
import com.roomyapp.entity.User;
import com.roomyapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="http://localhost:3000")
public class AuthController {

    private final UserService userService;

    //Constructor con inyección de dependencia de servicio de usuario
    public AuthController(UserService userService){
        this.userService=userService;
    }

    //Convierte la petición de React (JSON) en objeto LoginRequest
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        //Este controller llama al servicio de User (userService)
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

    //registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }
}
