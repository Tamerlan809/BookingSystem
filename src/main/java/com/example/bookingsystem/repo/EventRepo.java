package com.example.bookingsystem.repo;

import com.example.bookingsystem.dtos.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepo extends JpaRepository<RoomEvent, Long> {

    List<RoomEvent> findByRoomNo(String roomNo);

}
