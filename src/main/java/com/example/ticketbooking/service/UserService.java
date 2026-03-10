package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.UserRequest;
import com.example.ticketbooking.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRequest request);
}