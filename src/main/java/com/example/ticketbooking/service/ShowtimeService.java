package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.CreateShowtimeRequest;
import com.example.ticketbooking.dto.ShowtimeResponse;
import com.example.ticketbooking.model.Showtime;

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
    List<ShowtimeResponse> getShowtimesByMovieIdAndDate(Long movieId, LocalDateTime date);
    List<ShowtimeResponse> getShowtimesByCinemaRoomIdAndDate(Long cinemaRoomId, LocalDateTime date);
}