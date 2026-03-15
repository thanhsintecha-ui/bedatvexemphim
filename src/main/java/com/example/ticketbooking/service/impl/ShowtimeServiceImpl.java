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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ShowtimeServiceImpl implements ShowtimeService {

        private final ShowtimeRepository showtimeRepository;
        private final MovieRepository movieRepository;
        private final CinemaRoomRepository cinemaRoomRepository;

        public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository, MovieRepository movieRepository,
                        CinemaRoomRepository cinemaRoomRepository) {
                this.showtimeRepository = showtimeRepository;
                this.movieRepository = movieRepository;
                this.cinemaRoomRepository = cinemaRoomRepository;
        }

        @Override
        @Transactional
        public Showtime addShowtime(CreateShowtimeRequest request) {
                Movie movie = movieRepository.findById(request.getMovieId())
                                .orElseThrow(() -> new MovieNotFoundException(
                                                "Movie not found with id " + request.getMovieId()));

                CinemaRoom cinemaRoom = cinemaRoomRepository.findById(request.getCinemaRoomId())
                                .orElseThrow(() -> new CinemaRoomNotFoundException(
                                                "Cinema room not found with id " + request.getCinemaRoomId()));

                LocalDateTime endTime = request.getStartTime().plusMinutes(movie.getDuration());

                List<Showtime> overlappingShowtimes = showtimeRepository.findOverlappingShowtimes(
                                request.getMovieId(),
                                request.getCinemaRoomId(),
                                request.getStartTime(),
                                endTime);

                if (!overlappingShowtimes.isEmpty()) {
                        throw new ShowtimeConflictException(
                                        "There is already a showtime scheduled in this cinema room during the requested time period");
                }

                Showtime showtime = new Showtime();
                showtime.setMovie(movie);
                showtime.setCinemaRoom(cinemaRoom);
                showtime.setStartTime(request.getStartTime());
                showtime.setEndTime(endTime);
                showtime.setPrice(request.getPrice());
                showtime.setAvailableSeats(cinemaRoom.getTotalSeats());
                showtime.setFormat(request.getFormat());

                return showtimeRepository.save(showtime);
        }

        @Override
        @Transactional
        public Showtime updateShowtime(Long id, CreateShowtimeRequest request) {
                Showtime existingShowtime = showtimeRepository.findById(id)
                                .orElseThrow(() -> new ShowtimeConflictException("Showtime not found with id " + id));

                Movie movie = movieRepository.findById(request.getMovieId())
                                .orElseThrow(() -> new MovieNotFoundException(
                                                "Movie not found with id " + request.getMovieId()));

                CinemaRoom cinemaRoom = cinemaRoomRepository.findById(request.getCinemaRoomId())
                                .orElseThrow(() -> new CinemaRoomNotFoundException(
                                                "Cinema room not found with id " + request.getCinemaRoomId()));

                LocalDateTime endTime = request.getStartTime().plusMinutes(movie.getDuration());

                List<Showtime> overlappingShowtimes = showtimeRepository.findOverlappingShowtimes(
                                request.getMovieId(),
                                request.getCinemaRoomId(),
                                request.getStartTime(),
                                endTime);

                overlappingShowtimes.removeIf(s -> s.getId().equals(id));

                if (!overlappingShowtimes.isEmpty()) {
                        throw new ShowtimeConflictException(
                                        "There is already a showtime scheduled in this cinema room during the requested time period");
                }

                existingShowtime.setMovie(movie);
                existingShowtime.setCinemaRoom(cinemaRoom);
                existingShowtime.setStartTime(request.getStartTime());
                existingShowtime.setEndTime(endTime);
                existingShowtime.setPrice(request.getPrice());
                existingShowtime.setAvailableSeats(cinemaRoom.getTotalSeats());
                existingShowtime.setFormat(request.getFormat());

                return showtimeRepository.save(existingShowtime);
        }

        @Override
        @Transactional
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
        public List<ShowtimeResponse> getShowtimesByMovieIdAndDate(Long movieId, LocalDate date) {
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
                return showtimeRepository.findByMovieIdAndDate(movieId, startOfDay, endOfDay).stream()
                                .map(this::convertToResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public List<ShowtimeResponse> getShowtimesByCinemaRoomIdAndDate(Long cinemaRoomId, LocalDate date) {
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
                return showtimeRepository.findByCinemaRoomIdAndDate(cinemaRoomId, startOfDay, endOfDay).stream()
                                .map(this::convertToResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public List<ShowtimeResponse> getShowtimesByCinemaIdAndDate(Long cinemaId, LocalDate date) {
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
                return showtimeRepository.findByCinemaIdAndDate(cinemaId, startOfDay, endOfDay).stream()
                                .map(this::convertToResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public List<ShowtimeResponse> getShowtimesByDate(LocalDate date) {
                LocalDateTime startOfDay = date.atStartOfDay();
                LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
                return showtimeRepository.findByDate(startOfDay, endOfDay).stream()
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
                                showtime.getCinemaRoom().getCinema().getId(),
                                showtime.getCinemaRoom().getCinema().getName(),
                                showtime.getStartTime(),
                                showtime.getEndTime(),
                                showtime.getPrice(),
                                showtime.getAvailableSeats(),
                                showtime.getFormat(),
                                showtime.getCreatedAt(),
                                showtime.getUpdatedAt());
        }
}