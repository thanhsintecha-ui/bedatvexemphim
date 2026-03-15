package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.ApiResponse;
import com.example.ticketbooking.dto.CreateCinemaRoomRequest;
import com.example.ticketbooking.dto.CinemaRoomResponse;
import com.example.ticketbooking.service.CinemaRoomService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/cinema-rooms")
public class CinemaRoomController {
    private final CinemaRoomService cinemaRoomService;

    public CinemaRoomController(CinemaRoomService cinemaRoomService) {
        this.cinemaRoomService = cinemaRoomService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CinemaRoomResponse>> addCinemaRoom(@RequestBody CreateCinemaRoomRequest request) {
        var cinemaRoom = cinemaRoomService.addCinemaRoom(request);
        ApiResponse<CinemaRoomResponse> apiResponse = new ApiResponse<>(
                201,
                "Thêm phòng chiếu thành công",
                new CinemaRoomResponse(
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
                        cinemaRoom.getUpdatedAt()),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateCinemaRoom(@PathVariable Long id,
            @RequestBody CreateCinemaRoomRequest request) {
        cinemaRoomService.updateCinemaRoom(id, request);
        ApiResponse<Void> apiResponse = new ApiResponse<>(200, "Cập nhật phòng chiếu thành công", null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCinemaRoom(@PathVariable Long id) {
        cinemaRoomService.deleteCinemaRoom(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(200, "Xóa phòng chiếu thành công", null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CinemaRoomResponse>> getCinemaRoom(@PathVariable Long id) {
        var cinemaRoom = cinemaRoomService.getCinemaRoomById(id);
        ApiResponse<CinemaRoomResponse> apiResponse = new ApiResponse<>(
                200,
                "Lấy thông tin phòng chiếu thành công",
                new CinemaRoomResponse(
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
                        cinemaRoom.getUpdatedAt()),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CinemaRoomResponse>>> getAllCinemaRooms() {
        List<CinemaRoomResponse> responses = cinemaRoomService.getAllCinemaRooms();
        ApiResponse<List<CinemaRoomResponse>> apiResponse = new ApiResponse<>(
                200,
                "Danh sách phòng chiếu",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }
}