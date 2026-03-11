package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByMovieId(Long movieId);

    List<Showtime> findByCinemaRoomId(Long cinemaRoomId);

    List<Showtime> findByMovieIdAndStartTimeAfter(Long movieId, LocalDateTime startTime);

    List<Showtime> findByCinemaRoomIdAndStartTimeAfter(Long cinemaRoomId, LocalDateTime startTime);

    @Query("SELECT s FROM Showtime s WHERE s.movie.id = :movieId AND s.cinemaRoom.id = :cinemaRoomId AND s.startTime BETWEEN :startTime AND :endTime")
    List<Showtime> findOverlappingShowtimes(
        @Param("movieId") Long movieId,
        @Param("cinemaRoomId") Long cinemaRoomId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
}