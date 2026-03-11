package com.example.ticketbooking.dto;

import java.time.LocalDateTime;

public class ShowtimeResponse {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private Long cinemaRoomId;
    private String cinemaRoomNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
    private Integer availableSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ShowtimeResponse() {}

    public ShowtimeResponse(Long id, Long movieId, String movieTitle, Long cinemaRoomId, String cinemaRoomNumber, LocalDateTime startTime, LocalDateTime endTime, Double price, Integer availableSeats, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.cinemaRoomId = cinemaRoomId;
        this.cinemaRoomNumber = cinemaRoomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.availableSeats = availableSeats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public Long getCinemaRoomId() { return cinemaRoomId; }
    public void setCinemaRoomId(Long cinemaRoomId) { this.cinemaRoomId = cinemaRoomId; }

    public String getCinemaRoomNumber() { return cinemaRoomNumber; }
    public void setCinemaRoomNumber(String cinemaRoomNumber) { this.cinemaRoomNumber = cinemaRoomNumber; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}