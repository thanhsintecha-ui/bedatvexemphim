package com.example.ticketbooking.service.impl;

import com.example.ticketbooking.dto.CreateShowtimeRequest;
import com.example.ticketbooking.dto.ShowtimeResponse;
import com.example.ticketbooking.exception.CinemaRoomNotFoundException;
import com.example.ticketbooking.exception.MovieNotFoundException;
import com.example.ticketbooking.exception.ShowtimeConflictException;
import com.example.ticketbooking.model.CinemaRoom;
import com.example.ticketbooking.model.Movie;
import com.example.ticketbooking.model.Showtime;
import com.example.ticketbooking.repository.CinemaRoomRepository;
import com.example.ticketbooking.repository.MovieRepository;
import com.example.ticketbooking.repository.ShowtimeRepository;
import com.example.ticketbooking.service.ShowtimeService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository, MovieRepository movieRepository, CinemaRoomRepository cinemaRoomRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.cinemaRoomRepository = cinemaRoomRepository;
    }

    @Override
    public Showtime addShowtime(CreateShowtimeRequest request) {
        // Validate movie exists
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + request.getMovieId()));

        // Validate cinema room exists
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(request.getCinemaRoomId())
                .orElseThrow(() -> new CinemaRoomNotFoundException("Cinema room not found with id " + request.getCinemaRoomId()));

        // Calculate end time based on movie duration
        LocalDateTime endTime = request.getStartTime().plusMinutes(movie.getDuration());

        // Check for conflicts with existing showtimes in the same cinema room
        List<Showtime> overlappingShowtimes = showtimeRepository.findOverlappingShowtimes(
                request.getMovieId(),
                request.getCinemaRoomId(),
                request.getStartTime(),
                endTime
        );

        if (!overlappingShowtimes.isEmpty()) {
            throw new ShowtimeConflictException("There is already a showtime scheduled in this cinema room during the requested time period");
        }

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setCinemaRoom(cinemaRoom);
        showtime.setStartTime(request.getStartTime());
        showtime.setEndTime(endTime);
        showtime.setPrice(request.getPrice());
        showtime.setAvailableSeats(cinemaRoom.getTotalSeats());

        return showtimeRepository.save(showtime);
    }

    @Override
    public Showtime updateShowtime(Long id, CreateShowtimeRequest request) {
        Showtime existingShowtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new ShowtimeConflictException("Showtime not found with id " + id));

        // Validate movie exists
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + request.getMovieId()));

        // Validate cinema room exists
        CinemaRoom cinemaRoom = cinemaRoomRepository.findById(request.getCinemaRoomId())
                .orElseThrow(() -> new CinemaRoomNotFoundException("Cinema room not found with id " + request.getCinemaRoomId()));

        // Calculate end time based on movie duration
        LocalDateTime endTime = request.getStartTime().plusMinutes(movie.getDuration());

        // Check for conflicts with other showtimes in the same cinema room (excluding current showtime)
        List<Showtime> overlappingShowtimes = showtimeRepository.findOverlappingShowtimes(
                request.getMovieId(),
                request.getCinemaRoomId(),
                request.getStartTime(),
                endTime
        );

        // Filter out the current showtime from conflicts
        overlappingShowtimes.removeIf(s -> s.getId().equals(id));

        if (!overlappingShowtimes.isEmpty()) {
            throw new ShowtimeConflictException("There is already a showtime scheduled in this cinema room during the requested time period");
        }

        existingShowtime.setMovie(movie);
        existingShowtime.setCinemaRoom(cinemaRoom);
        existingShowtime.setStartTime(request.getStartTime());
        existingShowtime.setEndTime(endTime);
        existingShowtime.setPrice(request.getPrice());
        existingShowtime.setAvailableSeats(cinemaRoom.getTotalSeats());

        return showtimeRepository.save(existingShowtime);
    }

    @Override
    public void deleteShowtime(Long id) {
        if (!showtimeRepository.existsById(id)) {
            throw new ShowtimeConflictException("Showtime not found with id " + id);
        }
        showtimeRepository.deleteById(id);
    }

    @Override
    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new ShowtimeConflictException("Showtime not found with id " + id));
    }

    @Override
    public List<ShowtimeResponse> getAllShowtimes() {
        return showtimeRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeResponse> getShowtimesByMovieId(Long movieId) {
        return showtimeRepository.findByMovieId(movieId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeResponse> getShowtimesByCinemaRoomId(Long cinemaRoomId) {
        return showtimeRepository.findByCinemaRoomId(cinemaRoomId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeResponse> getShowtimesByMovieIdAndDate(Long movieId, LocalDateTime date) {
        LocalDateTime startOfDay = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = date.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return showtimeRepository.findByMovieIdAndStartTimeAfter(movieId, startOfDay).stream()
                .filter(s -> !s.getStartTime().isAfter(endOfDay))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeResponse> getShowtimesByCinemaRoomIdAndDate(Long cinemaRoomId, LocalDateTime date) {
        LocalDateTime startOfDay = date.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = date.withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return showtimeRepository.findByCinemaRoomIdAndStartTimeAfter(cinemaRoomId, startOfDay).stream()
                .filter(s -> !s.getStartTime().isAfter(endOfDay))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ShowtimeResponse convertToResponse(Showtime showtime) {
        return new ShowtimeResponse(
                showtime.getId(),
                showtime.getMovie().getId(),
                showtime.getMovie().getTitle(),
                showtime.getCinemaRoom().getId(),
                showtime.getCinemaRoom().getRoomNumber(),
                showtime.getStartTime(),
                showtime.getEndTime(),
                showtime.getPrice(),
                showtime.getAvailableSeats(),
                showtime.getCreatedAt(),
                showtime.getUpdatedAt()
        );
    }
}