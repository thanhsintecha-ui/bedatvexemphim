package com.example.ticketbooking.dto;

import java.time.LocalDateTime;

public class CinemaRoomResponse {
    private Long id;
    private Long cinemaId;
    private String cinemaName;
    private String roomNumber;
    private Integer totalSeats;
    private String roomType;
    private Integer rowCount;
    private Integer seatsPerRow;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CinemaRoomResponse() {
    }

    public CinemaRoomResponse(Long id, Long cinemaId, String cinemaName, String roomNumber, Integer totalSeats,
            String roomType, Integer rowCount, Integer seatsPerRow, String description, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.roomNumber = roomNumber;
        this.totalSeats = totalSeats;
        this.roomType = roomType;
        this.rowCount = rowCount;
        this.seatsPerRow = seatsPerRow;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}