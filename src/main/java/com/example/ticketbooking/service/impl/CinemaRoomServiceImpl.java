package com.example.ticketbooking.service.impl;

import com.example.ticketbooking.dto.CreateCinemaRoomRequest;
import com.example.ticketbooking.dto.CinemaRoomResponse;
import com.example.ticketbooking.exception.CinemaRoomNotFoundException;
import com.example.ticketbooking.model.CinemaRoom;
import com.example.ticketbooking.repository.CinemaRoomRepository;
import com.example.ticketbooking.service.CinemaRoomService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaRoomServiceImpl implements CinemaRoomService {
    private final CinemaRoomRepository cinemaRoomRepository;
    private final com.example.ticketbooking.repository.CinemaRepository cinemaRepository;

    public CinemaRoomServiceImpl(CinemaRoomRepository cinemaRoomRepository,
            com.example.ticketbooking.repository.CinemaRepository cinemaRepository) {
        this.cinemaRoomRepository = cinemaRoomRepository;
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public CinemaRoom addCinemaRoom(CreateCinemaRoomRequest request) {
        // Check if room number already exists
        if (cinemaRoomRepository.findByRoomNumber(request.getRoomNumber()).isPresent()) {
            throw new IllegalArgumentException(
                    "Cinema room with number " + request.getRoomNumber() + " already exists");
        }

        com.example.ticketbooking.model.Cinema cinema = cinemaRepository.findById(request.getCinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found with id " + request.getCinemaId()));

        CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setCinema(cinema);
        cinemaRoom.setRoomNumber(request.getRoomNumber());
        cinemaRoom.setTotalSeats(request.getTotalSeats());
        cinemaRoom.setRoomType(request.getRoomType());
        cinemaRoom.setRowCount(request.getRowCount());
        cinemaRoom.setSeatsPerRow(request.getSeatsPerRow());
        cinemaRoom.setDescription(request.getDescription());

        return cinemaRoomRepository.save(cinemaRoom);
    }

    @Override
    public CinemaRoom updateCinemaRoom(Long id, CreateCinemaRoomRequest request) {
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new CinemaRoomNotFoundException("Cinema room not found with id " + id));

        // Check if room number already exists for another room
        if (!cinemaRoom.getRoomNumber().equals(request.getRoomNumber()) &&
                cinemaRoomRepository.findByRoomNumber(request.getRoomNumber()).isPresent()) {
            throw new IllegalArgumentException(
                    "Cinema room with number " + request.getRoomNumber() + " already exists");
        }

        com.example.ticketbooking.model.Cinema cinema = cinemaRepository.findById(request.getCinemaId())
                .orElseThrow(() -> new RuntimeException("Cinema not found with id " + request.getCinemaId()));

        cinemaRoom.setCinema(cinema);
        cinemaRoom.setRoomNumber(request.getRoomNumber());
        cinemaRoom.setTotalSeats(request.getTotalSeats());
        cinemaRoom.setRoomType(request.getRoomType());
        cinemaRoom.setRowCount(request.getRowCount());
        cinemaRoom.setSeatsPerRow(request.getSeatsPerRow());
        cinemaRoom.setDescription(request.getDescription());

        return cinemaRoomRepository.save(cinemaRoom);
    }

    @Override
    public void deleteCinemaRoom(Long id) {
        if (!cinemaRoomRepository.existsById(id)) {
            throw new CinemaRoomNotFoundException("Cinema room not found with id " + id);
        }
        cinemaRoomRepository.deleteById(id);
    }

    @Override
    public CinemaRoom getCinemaRoomById(Long id) {
        return cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new CinemaRoomNotFoundException("Cinema room not found with id " + id));
    }

    @Override
    public List<CinemaRoomResponse> getAllCinemaRooms() {
        return cinemaRoomRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private CinemaRoomResponse convertToResponse(CinemaRoom cinemaRoom) {
        return new CinemaRoomResponse(
                cinemaRoom.getId(),
                cinemaRoom.getCinema().getId(),
                cinemaRoom.getCinema().getName(),
                cinemaRoom.getRoomNumber(),
                cinemaRoom.getTotalSeats(),
                cinemaRoom.getRoomType(),
                cinemaRoom.getRowCount(),
                cinemaRoom.getSeatsPerRow(),
                cinemaRoom.getDescription(),
                cinemaRoom.getCreatedAt(),
                cinemaRoom.getUpdatedAt());
    }
}