package com.moviecollection.MovieCollection.service;

import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.repository.MovieRepository;
import com.moviecollection.MovieCollection.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Movie createMovie(Movie movie) throws Exception{
        UserEntity userEntity = userService.getUserFromPrincipal();
        movie.setOwner(userEntity);
        userEntity.getCreatedMovies().add(movie.toEntity());
        return movie;
    }

    public List<Movie> getAllMovies(){
        List<UserEntity> userEntities = userRepository.findAll();
        List<Movie> allMovies = new ArrayList<>();
        userEntities.forEach(userEntity -> {
            userEntity.getCreatedMovies().forEach(movieEntity -> {
                allMovies.add(Movie.fromEntity(movieEntity));
            });
        });
        return allMovies;
    }
}
