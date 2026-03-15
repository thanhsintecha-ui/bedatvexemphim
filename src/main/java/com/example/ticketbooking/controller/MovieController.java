package com.example.ticketbooking.controller;

import com.example.ticketbooking.dto.ApiResponse;
import com.example.ticketbooking.dto.MovieRequest;
import com.example.ticketbooking.dto.MovieResponse;
import com.example.ticketbooking.model.Movie;
import com.example.ticketbooking.service.MovieService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponse>> addMovie(@RequestBody MovieRequest request) {
        Movie movie = movieService.addMovie(request);
        ApiResponse<MovieResponse> apiResponse = new ApiResponse<>(
                200,
                "Thêm phim thành công",
                new MovieResponse(movie.getId(), movie.getTitle(), movie.getDuration()),
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateMovie(@PathVariable Long id, @RequestBody MovieRequest request) {
        movieService.updateMovie(id, request);
        ApiResponse<Void> apiResponse = new ApiResponse<>(200, "Cập nhật phim thành công", null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>(200, "Xóa phim thành công", null,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        MovieResponse response = new MovieResponse(movie.getId(), movie.getTitle(), movie.getDuration());
        ApiResponse<MovieResponse> apiResponse = new ApiResponse<>(
                200,
                "Lấy phim thành công",
                response,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies() {
        List<MovieResponse> responses = movieService.getAllMovies();
        ApiResponse<List<MovieResponse>> apiResponse = new ApiResponse<>(
                200,
                "Danh sách phim",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/now-showing")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getNowShowingMovies() {
        List<MovieResponse> responses = movieService.getNowShowingMovies();
        ApiResponse<List<MovieResponse>> apiResponse = new ApiResponse<>(
                200,
                "Danh sách phim đang chiếu",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/coming-soon")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getComingSoonMovies() {
        List<MovieResponse> responses = movieService.getComingSoonMovies();
        ApiResponse<List<MovieResponse>> apiResponse = new ApiResponse<>(
                200,
                "Danh sách phim sắp chiếu",
                responses,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(apiResponse);
    }
}