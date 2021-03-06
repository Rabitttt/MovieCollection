package com.moviecollection.MovieCollection.service;

import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;

    public Movie createMovie(Movie movie){
        return movie;
        //userService.getUserByAuthUsers();
    }
}
