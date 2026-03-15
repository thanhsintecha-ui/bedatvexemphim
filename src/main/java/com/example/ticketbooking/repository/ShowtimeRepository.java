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

    // Improved: JPQL BETWEEN thay thế in-memory filter cho phim + ngày
    @Query("SELECT s FROM Showtime s JOIN FETCH s.cinemaRoom cr JOIN FETCH cr.cinema " +
            "WHERE s.movie.id = :movieId AND s.startTime BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY s.startTime")
    List<Showtime> findByMovieIdAndDate(
            @Param("movieId") Long movieId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    // Improved: JPQL BETWEEN thay thế in-memory filter cho phòng chiếu + ngày
    @Query("SELECT s FROM Showtime s JOIN FETCH s.movie JOIN FETCH s.cinemaRoom cr JOIN FETCH cr.cinema " +
            "WHERE cr.id = :cinemaRoomId AND s.startTime BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY s.startTime")
    List<Showtime> findByCinemaRoomIdAndDate(
            @Param("cinemaRoomId") Long cinemaRoomId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    // Lịch chiếu theo rạp + ngày (qua cinema_room → cinema)
    @Query("SELECT s FROM Showtime s JOIN FETCH s.movie JOIN FETCH s.cinemaRoom cr JOIN FETCH cr.cinema c " +
            "WHERE c.id = :cinemaId AND s.startTime BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY s.startTime")
    List<Showtime> findByCinemaIdAndDate(
            @Param("cinemaId") Long cinemaId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    // Lịch chiếu theo ngày (tất cả rạp)
    @Query("SELECT s FROM Showtime s JOIN FETCH s.movie JOIN FETCH s.cinemaRoom cr JOIN FETCH cr.cinema " +
            "WHERE s.startTime BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY s.startTime")
    List<Showtime> findByDate(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT s FROM Showtime s WHERE s.movie.id = :movieId AND s.cinemaRoom.id = :cinemaRoomId " +
            "AND s.startTime BETWEEN :startTime AND :endTime")
    List<Showtime> findOverlappingShowtimes(
            @Param("movieId") Long movieId,
            @Param("cinemaRoomId") Long cinemaRoomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}