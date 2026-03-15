package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.UpdateUserRequest;
import com.example.ticketbooking.dto.UserRequest;
import com.example.ticketbooking.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    List<UserResponse> getAllUsers();

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}