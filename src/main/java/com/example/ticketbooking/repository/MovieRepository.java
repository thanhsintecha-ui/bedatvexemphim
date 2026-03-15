package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Movie;
import com.example.ticketbooking.model.MovieStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByStatus(MovieStatus status);
}