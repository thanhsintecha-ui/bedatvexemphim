package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.CreateShowtimeRequest;
import com.example.ticketbooking.dto.ShowtimeResponse;
import com.example.ticketbooking.model.Showtime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeService {
    Showtime addShowtime(CreateShowtimeRequest request);

    Showtime updateShowtime(Long id, CreateShowtimeRequest request);

    void deleteShowtime(Long id);

    Showtime getShowtimeById(Long id);

    List<ShowtimeResponse> getAllShowtimes();

    List<ShowtimeResponse> getShowtimesByMovieId(Long movieId);

    List<ShowtimeResponse> getShowtimesByCinemaRoomId(Long cinemaRoomId);

    // Improved: dùng LocalDate thay vì LocalDateTime + in-memory filter
    List<ShowtimeResponse> getShowtimesByMovieIdAndDate(Long movieId, LocalDate date);

    List<ShowtimeResponse> getShowtimesByCinemaRoomIdAndDate(Long cinemaRoomId, LocalDate date);

    // Mới: tra cứu theo rạp + ngày
    List<ShowtimeResponse> getShowtimesByCinemaIdAndDate(Long cinemaId, LocalDate date);

    // Mới: tra cứu theo ngày (tất cả rạp)
    List<ShowtimeResponse> getShowtimesByDate(LocalDate date);
}