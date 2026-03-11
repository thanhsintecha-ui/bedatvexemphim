package com.example.ticketbooking.exceptionhandler;

import com.example.ticketbooking.exception.CinemaRoomNotFoundException;
import com.example.ticketbooking.exception.MovieNotFoundException;
import com.example.ticketbooking.exception.ShowtimeConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<String> handleMovieNotFound(MovieNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phim: " + ex.getMessage());
    }

    @ExceptionHandler(CinemaRoomNotFoundException.class)
    public ResponseEntity<String> handleCinemaRoomNotFound(CinemaRoomNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Không tìm thấy phòng chiếu: " + ex.getMessage());
    }

    @ExceptionHandler(ShowtimeConflictException.class)
    public ResponseEntity<String> handleShowtimeConflict(ShowtimeConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Xung đột suất chiếu: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi hệ thống: " + ex.getMessage());
    }
}