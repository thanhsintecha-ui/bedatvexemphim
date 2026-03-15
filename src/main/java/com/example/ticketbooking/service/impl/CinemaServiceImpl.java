package com.example.ticketbooking.service.impl;

import com.example.ticketbooking.dto.CinemaRequest;
import com.example.ticketbooking.dto.CinemaResponse;
import com.example.ticketbooking.model.Cinema;
import com.example.ticketbooking.repository.CinemaRepository;
import com.example.ticketbooking.service.CinemaService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public Cinema addCinema(CinemaRequest request) {
        Cinema cinema = new Cinema();
        cinema.setName(request.getName());
        cinema.setAddress(request.getAddress());
        cinema.setCity(request.getCity());
        return cinemaRepository.save(cinema);
    }

    @Override
    public Cinema updateCinema(Long id, CinemaRequest request) {
        Cinema cinema = getCinemaById(id);
        cinema.setName(request.getName());
        cinema.setAddress(request.getAddress());
        cinema.setCity(request.getCity());
        return cinemaRepository.save(cinema);
    }

    @Override
    public void deleteCinema(Long id) {
        if (!cinemaRepository.existsById(id)) {
            throw new RuntimeException("Cinema not found with id " + id);
        }
        cinemaRepository.deleteById(id);
    }

    @Override
    public Cinema getCinemaById(Long id) {
        return cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id " + id));
    }

    @Override
    public List<CinemaResponse> getAllCinemas() {
        return cinemaRepository.findAll().stream()
                .map(CinemaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CinemaResponse> getCinemasByCity(String city) {
        return cinemaRepository.findByCity(city).stream()
                .map(CinemaResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
