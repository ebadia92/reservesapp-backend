package com.roomyapp.controller;

import com.roomyapp.config.JwtUtil;
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
    private final JwtUtil jwtUtil;

    //Constructor con inyección de dependencia de servicio de usuario
    public AuthController(UserService userService, JwtUtil jwtUtil){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }

    //Convierte la petición de React (JSON) en objeto LoginRequest
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        //Este controller llama al servicio de User (userService)
        User user= userService.login(
                request.getEmail(),
                request.getPassword()
        );

        String token= jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(), //.name() convierte a String y el frontend recibe ADMIN o EMPLOYEE, porque Role es enum
                token
        );
    }

    //Register
    @PostMapping("/register")
    public LoginResponse register(@RequestBody RegisterRequest request){

        User user= userService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(), //.name() convierte a String y el frontend recibe ADMIN o EMPLOYEE, porque Role es enum
                token
        );
    }

    //registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }
}
