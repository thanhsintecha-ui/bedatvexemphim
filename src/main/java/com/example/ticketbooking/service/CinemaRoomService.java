package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.CreateCinemaRoomRequest;
import com.example.ticketbooking.dto.CinemaRoomResponse;
import com.example.ticketbooking.model.CinemaRoom;

import java.util.List;

public interface CinemaRoomService {
    CinemaRoom addCinemaRoom(CreateCinemaRoomRequest request);
    CinemaRoom updateCinemaRoom(Long id, CreateCinemaRoomRequest request);
    void deleteCinemaRoom(Long id);
    CinemaRoom getCinemaRoomById(Long id);
    List<CinemaRoomResponse> getAllCinemaRooms();
}