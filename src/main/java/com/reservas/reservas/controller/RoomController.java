package com.reservas.reservas.controller;

import com.reservas.reservas.entity.Room;
import com.reservas.reservas.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private RoomService roomService;

    //Constructor con inyeccion del servicio como parámetro
    public RoomController (RoomService roomService){
        this.roomService =roomService;
    }

    //Traemos salas
    @GetMapping
    public List <Room> getRooms(){
        return roomService.getRooms();
    }

    //Anadimos salas
    @PostMapping
    public Room createRooms(@RequestBody Room room){
        return roomService.createRoom(room);
    }

    //Eliminamos sala
    @DeleteMapping("/{name}")
    public void deleteRooms(@PathVariable String name){
        roomService.deleteRoom(name);
    }

}
