package com.example.ticketbooking.dto;

import com.example.ticketbooking.model.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String userId;
    private String role;
    private String fullName;
    private String phone;
    private LocalDate birthDate;
    private Gender gender;
    private LocalDateTime createdAt;

    // Default constructor
    public UserResponse() {
    }

    // Constructor with parameters
    public UserResponse(Long id, String username, String email, String userId, String role,
            String fullName, String phone, LocalDate birthDate, Gender gender,
            LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.role = role;
        this.fullName = fullName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static UserResponse fromEntity(com.example.ticketbooking.model.User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getUserId(),
                user.getRole().name(),
                user.getFullName(),
                user.getPhone(),
                user.getBirthDate(),
                user.getGender(),
                user.getCreatedAt());
    }
}