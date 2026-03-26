package com.roomyapp.repository;

import com.roomyapp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
  Interfaz repositorio de Entidad Room
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
