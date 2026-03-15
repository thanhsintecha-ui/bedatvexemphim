package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.ApiResponse;
import com.example.ticketbooking.dto.CinemaRequest;
import com.example.ticketbooking.dto.CinemaResponse;
import com.example.ticketbooking.service.CinemaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CinemaResponse>> addCinema(@RequestBody CinemaRequest request) {
        var cinema = cinemaService.addCinema(request);
        ApiResponse<CinemaResponse> apiResponse = new ApiResponse<>(
                201,
                "Thêm rạp thành công",
                CinemaResponse.fromEntity(cinema),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaResponse>> updateCinema(@PathVariable Long id,
            @RequestBody CinemaRequest request) {
        var cinema = cinemaService.updateCinema(id, request);
        ApiResponse<CinemaResponse> apiResponse = new ApiResponse<>(
                200,
                "Cập nhật rạp thành công",
                CinemaResponse.fromEntity(cinema),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                200,
                "Xóa rạp thành công",
                null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaResponse>> getCinema(@PathVariable Long id) {
        var cinema = cinemaService.getCinemaById(id);
        ApiResponse<CinemaResponse> apiResponse = new ApiResponse<>(
                200,
                "Lấy thông tin rạp thành công",
                CinemaResponse.fromEntity(cinema),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CinemaResponse>>> getAllCinemas() {
        List<CinemaResponse> responses = cinemaService.getAllCinemas();
        ApiResponse<List<CinemaResponse>> apiResponse = new ApiResponse<>(
                200,
                "Danh sách rạp",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<CinemaResponse>>> getCinemasByCity(@PathVariable String city) {
        List<CinemaResponse> responses = cinemaService.getCinemasByCity(city);
        ApiResponse<List<CinemaResponse>> apiResponse = new ApiResponse<>(
                200,
                "Danh sách rạp tại " + city,
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }
}
