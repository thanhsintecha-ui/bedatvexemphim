package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Optional: Custom queries if needed
}