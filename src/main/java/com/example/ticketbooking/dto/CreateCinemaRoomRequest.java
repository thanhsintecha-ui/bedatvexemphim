package com.example.ticketbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateCinemaRoomRequest {
    @NotNull(message = "Cinema ID is required")
    @Positive(message = "Cinema ID must be positive")
    private Long cinemaId;

    @NotBlank(message = "Room number is required")
    @Size(min = 1, max = 20, message = "Room number must be between 1 and 20 characters")
    private String roomNumber;

    @NotNull(message = "Total seats is required")
    @Positive(message = "Total seats must be positive")
    private Integer totalSeats;

    @NotBlank(message = "Room type is required")
    @Size(min = 1, max = 20, message = "Room type must be between 1 and 20 characters")
    private String roomType;

    @NotNull(message = "Number of rows is required")
    @Positive(message = "Number of rows must be positive")
    private Integer rowCount;

    @NotNull(message = "Seats per row is required")
    @Positive(message = "Seats per row must be positive")
    private Integer seatsPerRow;

    private String description;

    // Getters and setters
    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(Integer seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}