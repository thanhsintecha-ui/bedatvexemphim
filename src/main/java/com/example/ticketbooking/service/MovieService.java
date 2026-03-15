package com.example.ticketbooking.service;

import com.example.ticketbooking.exception.MovieNotFoundException;
import com.example.ticketbooking.model.Movie;
import com.example.ticketbooking.model.MovieStatus;
import com.example.ticketbooking.repository.MovieRepository;
import com.example.ticketbooking.dto.MovieRequest;
import com.example.ticketbooking.dto.MovieResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(MovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setStatus(request.getStatus());
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + id));
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setStatus(request.getStatus());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("Movie not found with id " + id);
        }
        movieRepository.deleteById(id);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id " + id));
    }

    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public List<MovieResponse> getNowShowingMovies() {
        return movieRepository.findByStatus(MovieStatus.NOW_SHOWING)
                .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public List<MovieResponse> getComingSoonMovies() {
        return movieRepository.findByStatus(MovieStatus.COMING_SOON)
                .stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    private MovieResponse convertToResponse(Movie movie) {
        MovieResponse response = new MovieResponse();
        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setDescription(movie.getDescription());
        response.setDuration(movie.getDuration());
        response.setGenre(movie.getGenre());
        response.setPosterUrl(movie.getPosterUrl());
        response.setStatus(movie.getStatus());
        response.setCreatedAt(movie.getCreatedAt());
        response.setUpdatedAt(movie.getUpdatedAt());
        return response;
    }
}