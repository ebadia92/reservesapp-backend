package com.roomyapp.service;

import com.roomyapp.entity.Room;
import com.roomyapp.repository.RoomRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    //Inyeccion de repositorio dentro del servicio
    public RoomService (RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }
    //Cambio en el get
    public List<Room> getRooms(){
        return roomRepository.findAll();
    }

   //Cambio en el post del map fake previo
    public Room createRoom(Room room){
        return roomRepository.save(room);
    }
}
