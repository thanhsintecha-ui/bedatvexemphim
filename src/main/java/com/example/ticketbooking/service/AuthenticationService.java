package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.LoginRequest;
import com.example.ticketbooking.dto.LoginResponse;
import com.example.ticketbooking.exception.InvalidCredentialsException;
import com.example.ticketbooking.model.User;
import com.example.ticketbooking.repository.UserRepository;
import com.example.ticketbooking.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                UserDetailsServiceImpl userDetailsService,
                                JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public LoginResponse authenticateUser(LoginRequest request) {
        // Authenticate user using password encoding
        Optional<User> user = userRepository.findByUsername(request.getUsernameOrEmail());
        if (user.isEmpty()) {
            user = userRepository.findByEmail(request.getUsernameOrEmail());
            if (user.isEmpty()) {
                throw new InvalidCredentialsException("Invalid username/email or password");
            }
        }

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsernameOrEmail());

        // Generate JWT token
        String token = jwtTokenUtil.generateToken(userDetails);

        // Return response
        return new LoginResponse(
            token,
            user.get().getId(),
            user.get().getUsername(),
            user.get().getEmail()
        );
    }
}