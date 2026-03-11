package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.ApiResponse;
import com.example.ticketbooking.dto.CreateShowtimeRequest;
import com.example.ticketbooking.dto.ShowtimeResponse;
import com.example.ticketbooking.service.ShowtimeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ShowtimeResponse>> addShowtime(@RequestBody CreateShowtimeRequest request) {
        var showtime = showtimeService.addShowtime(request);
        ApiResponse<ShowtimeResponse> apiResponse = new ApiResponse(
                201,
                "Thêm suất chiếu thành công",
                new ShowtimeResponse(
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
                ),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateShowtime(@PathVariable Long id, @RequestBody CreateShowtimeRequest request) {
        showtimeService.updateShowtime(id, request);
        ApiResponse<Void> apiResponse = new ApiResponse(200, "Cập nhật suất chiếu thành công", null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        ApiResponse<Void> apiResponse = new ApiResponse(200, "Xóa suất chiếu thành công", null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowtimeResponse>> getShowtime(@PathVariable Long id) {
        var showtime = showtimeService.getShowtimeById(id);
        ApiResponse<ShowtimeResponse> apiResponse = new ApiResponse(
                200,
                "Lấy thông tin suất chiếu thành công",
                new ShowtimeResponse(
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
                ),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getAllShowtimes() {
        List<ShowtimeResponse> responses = showtimeService.getAllShowtimes();
        ApiResponse<List<ShowtimeResponse>> apiResponse = new ApiResponse(
                200,
                "Danh sách suất chiếu",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByMovieId(@PathVariable Long movieId) {
        List<ShowtimeResponse> responses = showtimeService.getShowtimesByMovieId(movieId);
        ApiResponse<List<ShowtimeResponse>> apiResponse = new ApiResponse(
                200,
                "Danh sách suất chiếu của phim",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/cinema-room/{cinemaRoomId}")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByCinemaRoomId(@PathVariable Long cinemaRoomId) {
        List<ShowtimeResponse> responses = showtimeService.getShowtimesByCinemaRoomId(cinemaRoomId);
        ApiResponse<List<ShowtimeResponse>> apiResponse = new ApiResponse(
                200,
                "Danh sách suất chiếu của phòng chiếu",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/movie/{movieId}/date/{date}")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByMovieIdAndDate(
            @PathVariable Long movieId,
            @PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<ShowtimeResponse> responses = showtimeService.getShowtimesByMovieIdAndDate(movieId, dateTime);
        ApiResponse<List<ShowtimeResponse>> apiResponse = new ApiResponse(
                200,
                "Danh sách suất chiếu của phim trong ngày",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/cinema-room/{cinemaRoomId}/date/{date}")
    public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByCinemaRoomIdAndDate(
            @PathVariable Long cinemaRoomId,
            @PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<ShowtimeResponse> responses = showtimeService.getShowtimesByCinemaRoomIdAndDate(cinemaRoomId, dateTime);
        ApiResponse<List<ShowtimeResponse>> apiResponse = new ApiResponse(
                200,
                "Danh sách suất chiếu của phòng chiếu trong ngày",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(apiResponse);
    }
}