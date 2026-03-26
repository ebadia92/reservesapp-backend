package com.roomyapp.dto;
/*
Clase que representa un DTO de salida
Decide enviar al frontend los datos del usuario, pero exceptuando la password, esto es información sensible.
Solo transporta los datos ente las capas
Jackson necesita getters para convertir el objet a JSON, si no hay getters no aparece en JSON
 */
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String role;

    public LoginResponse(Long id, String name, String email, String role){
        this.id=id;
        this.name=name;
        this.email=email;
        this.role=role;
    }

    public Long getid(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getRole(){
        return role;
    }
}
