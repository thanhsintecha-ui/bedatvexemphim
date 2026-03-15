package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.CinemaRequest;
import com.example.ticketbooking.dto.CinemaResponse;
import com.example.ticketbooking.model.Cinema;
import java.util.List;

public interface CinemaService {
    Cinema addCinema(CinemaRequest request);

    Cinema updateCinema(Long id, CinemaRequest request);

    void deleteCinema(Long id);

    Cinema getCinemaById(Long id);

    List<CinemaResponse> getAllCinemas();

    List<CinemaResponse> getCinemasByCity(String city);
}
