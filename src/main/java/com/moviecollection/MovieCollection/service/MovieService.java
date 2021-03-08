package com.moviecollection.MovieCollection.service;

import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.entity.MovieEntity;
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
        movie.setCreator(User.fromEntity(userEntity));
        MovieEntity movieEntity = movie.toEntity();
        movieRepository.save(movieEntity);
        return Movie.fromEntity(movieEntity);
    }
    @Transactional
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
    @Transactional
    public Movie getMovieById(int id){
        return Movie.fromEntity(movieRepository.getOne(id));
    }

    @Transactional
    public void addMovieToCollection(Movie movie){
        UserEntity userEntity = userService.getUserFromPrincipal();
        MovieEntity movieEntity = movie.toEntity();
        userEntity.getOwnedMovies().add(movieEntity);

        movieRepository.save(movieEntity);
    }

    @Transactional
    public List<User> getMovieOwners(int movieId) {
        MovieEntity movieEntity = movieRepository.getOne(movieId);
        return Movie.movieOwnerListFromEntity(movieEntity);
    }

    @Transactional
    public Movie updateMovie(Movie movie){
        MovieEntity movieEntity = movieRepository.findById(movie.getId()).orElseThrow();
        movieEntity.setCategory(movie.getCategory());
        movieEntity.setDescription(movie.getDescription());
        movieEntity.setLanguage(movie.getLanguage());
        movieEntity.setReleaseDate(movie.getReleaseDate());
        movieEntity.setName(movie.getName());
        movieEntity = movieRepository.save(movieEntity);
        return Movie.fromEntity(movieEntity);

    }

}
