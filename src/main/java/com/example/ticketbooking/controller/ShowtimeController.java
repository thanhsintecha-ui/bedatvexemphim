package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.ApiResponse;
import com.example.ticketbooking.dto.CreateShowtimeRequest;
import com.example.ticketbooking.dto.ShowtimeResponse;
import com.example.ticketbooking.model.Showtime;
import com.example.ticketbooking.service.ShowtimeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
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
                Showtime showtime = showtimeService.addShowtime(request);
                return ResponseEntity.status(201).body(ok(201, "Thêm suất chiếu thành công", toResponse(showtime)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> updateShowtime(@PathVariable Long id,
                        @RequestBody CreateShowtimeRequest request) {
                showtimeService.updateShowtime(id, request);
                return ResponseEntity.ok(ok(200, "Cập nhật suất chiếu thành công", null));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteShowtime(@PathVariable Long id) {
                showtimeService.deleteShowtime(id);
                return ResponseEntity.ok(ok(200, "Xóa suất chiếu thành công", null));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ShowtimeResponse>> getShowtime(@PathVariable Long id) {
                Showtime showtime = showtimeService.getShowtimeById(id);
                return ResponseEntity.ok(ok(200, "Lấy thông tin suất chiếu thành công", toResponse(showtime)));
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getAllShowtimes() {
                return ResponseEntity.ok(ok(200, "Danh sách suất chiếu", showtimeService.getAllShowtimes()));
        }

        @GetMapping("/movie/{movieId}")
        public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByMovieId(@PathVariable Long movieId) {
                return ResponseEntity.ok(ok(200, "Danh sách suất chiếu của phim",
                                showtimeService.getShowtimesByMovieId(movieId)));
        }

        @GetMapping("/cinema-room/{cinemaRoomId}")
        public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByCinemaRoomId(
                        @PathVariable Long cinemaRoomId) {
                return ResponseEntity.ok(ok(200, "Danh sách suất chiếu của phòng chiếu",
                                showtimeService.getShowtimesByCinemaRoomId(cinemaRoomId)));
        }

        /**
         * Tra cứu lịch chiếu theo phim + ngày
         * GET /api/showtimes/movie/{movieId}/by-date?date=2026-03-15
         */
        @GetMapping("/movie/{movieId}/by-date")
        public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByMovieIdAndDate(
                        @PathVariable Long movieId,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
                return ResponseEntity.ok(ok(200, "Danh sách suất chiếu của phim trong ngày",
                                showtimeService.getShowtimesByMovieIdAndDate(movieId, date)));
        }

        /**
         * Tra cứu lịch chiếu theo phòng chiếu + ngày
         * GET /api/showtimes/cinema-room/{cinemaRoomId}/by-date?date=2026-03-15
         */
        @GetMapping("/cinema-room/{cinemaRoomId}/by-date")
        public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByCinemaRoomIdAndDate(
                        @PathVariable Long cinemaRoomId,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
                return ResponseEntity.ok(ok(200, "Danh sách suất chiếu của phòng chiếu trong ngày",
                                showtimeService.getShowtimesByCinemaRoomIdAndDate(cinemaRoomId, date)));
        }

        /**
         * Tra cứu lịch chiếu theo rạp + ngày
         * GET /api/showtimes/by-cinema?cinemaId=1&date=2026-03-15
         */
        @GetMapping("/by-cinema")
        public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> getShowtimesByCinemaIdAndDate(
                        @RequestParam Long cinemaId,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
                return ResponseEntity.ok(ok(200, "Lịch chiếu theo rạp và ngày",
                                showtimeService.getShowtimesByCinemaIdAndDate(cinemaId, date)));
        }

        /**
         * Tra cứu lịch chiếu theo ngày (tất cả rạp), có thể kèm cinemaId để lọc
         * GET /api/showtimes/search?date=2026-03-15
         * GET /api/showtimes/search?date=2026-03-15&cinemaId=1
         */
        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<ShowtimeResponse>>> searchShowtimes(
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                        @RequestParam(required = false) Long cinemaId) {
                List<ShowtimeResponse> responses = (cinemaId != null)
                                ? showtimeService.getShowtimesByCinemaIdAndDate(cinemaId, date)
                                : showtimeService.getShowtimesByDate(date);
                String message = (cinemaId != null)
                                ? "Lịch chiếu theo rạp và ngày"
                                : "Lịch chiếu theo ngày";
                return ResponseEntity.ok(ok(200, message, responses));
        }

        // ─── Private helper ───────────────────────────────────────────────────────

        private <T> ApiResponse<T> ok(int status, String message, T data) {
                return new ApiResponse<>(status, message, data,
                                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        private ShowtimeResponse toResponse(Showtime showtime) {
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