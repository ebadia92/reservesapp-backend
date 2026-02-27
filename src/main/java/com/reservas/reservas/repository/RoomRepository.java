package com.reservas.reservas.repository;

import com.reservas.reservas.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
  Interfaz repositorio de Entidad Room
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    void deleteByName(String name);
    Optional<Room> findByName(String name);
}
