package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {
    Optional<CinemaRoom> findByRoomNumber(String roomNumber);
}