package com.roomyapp.controller;

import com.roomyapp.entity.Room;
import com.roomyapp.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {
    private RoomService roomService;

    //Constructor con inyeccion del servicio como parámetro
    public RoomController (RoomService roomService){
        this.roomService =roomService;
    }

    //Actualizamos el get
    @GetMapping
    public List <Room> getRooms(){
        return roomService.getRooms();
    }

    //Actualizamos el post
    @PostMapping
    public Room createRooms(@RequestBody Room room){
        return roomService.createRoom(room);
    }

}
