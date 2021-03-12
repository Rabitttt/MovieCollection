package com.moviecollection.MovieCollection.service;

import com.moviecollection.MovieCollection.domain.Cast;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.entity.CastEntity;
import com.moviecollection.MovieCollection.entity.MovieEntity;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.enums.MovieCategories;
import com.moviecollection.MovieCollection.repository.CastRepository;
import com.moviecollection.MovieCollection.repository.MovieRepository;
import com.moviecollection.MovieCollection.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor //for dependency injection
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CastRepository castRepository;

    @Transactional
    public Movie createMovie(Movie movie) throws Exception{
        UserEntity userEntity = userService.getUserFromPrincipal();
        movie.setCreator(User.fromEntity(userEntity));
        MovieEntity movieEntity = movie.toEntity();
        addMovieToCollection(Movie.fromEntity(movieEntity)); //if I save on this function it will be twin data because I save in addMovieToCollection function
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

    public List<Cast> movieCastList(Movie movie){
        MovieEntity movieEntity = movieRepository.getOne(movie.toEntity().getId());
        return Cast.getMovieCastList(movieEntity);
    }
    @Transactional
    public void createCastForMovie(Cast cast,int movieId)
    {
        Movie movie = Movie.fromEntity(movieRepository.getOne(movieId));
        CastEntity castEntity = cast.toEntity();
        castEntity.setMovieEntity(movie.toEntity());
        castRepository.save(castEntity);
    }
    @Transactional
    public void addMovieToCollection(Movie movie){
        UserEntity userEntity = userService.getUserFromPrincipal();
        MovieEntity movieEntity = movie.toEntity();
        userEntity.getOwnedMovies().add(movieEntity);
        userRepository.save(userEntity);
    }
    @Transactional
    public List<User> getMovieOwners(int movieId) {
        MovieEntity movieEntity = movieRepository.getOne(movieId);
        return Movie.movieOwnerListFromEntity(movieEntity);
    }
    @Transactional
    public List<Cast> getMovieCastList(int movieId){
        MovieEntity movieEntity = movieRepository.getOne(movieId);
        List<CastEntity> castEntities = castRepository.findAllByMovieEntity(movieId);
        List<Cast> castList = new ArrayList<>();
        castEntities.forEach(castEntity -> {
            castList.add(Cast.fromEntity(castEntity));
        });

        return castList;
    }

    @Transactional
    public int deleteCastFromMovie( int castId){
        CastEntity castEntity = castRepository.getOne(castId);
        int movieId = castEntity.getMovieEntity().getId();
        castRepository.delete(castEntity);
        return movieId;
    }

    @Transactional
    public Movie updateMovie(Movie movie){
        MovieEntity movieEntity = movieRepository.findById(movie.getId()).orElseThrow();
        movieEntity.setCategory(movie.getCategory());
        movieEntity.setDescription(movie.getDescription());
        movieEntity.setLanguage(movie.getLanguage());
        if(movie.getReleaseDate() != null)
        {
            movieEntity.setReleaseDate(movieEntity.getReleaseDate());
        }

        movieEntity.setName(movie.getName());
        movieEntity = movieRepository.save(movieEntity);
        return Movie.fromEntity(movieEntity);

    }

    @Transactional
    public void removeMovieFromCollection(int movieId){

        MovieEntity movieEntity = movieRepository.findById(movieId).orElseThrow();
        UserEntity authUser = userService.getUserFromPrincipal();
        authUser.getOwnedMovies().remove(movieEntity);
        userRepository.save(authUser);
    }

    @Transactional
    public void deleteMovie(int movieId){
        MovieEntity movieEntity = movieRepository.findById(movieId).orElseThrow();
        for (UserEntity userEntity: movieEntity.getOwnerList()) {
            userEntity.getOwnedMovies().remove(movieEntity);
        }
        movieRepository.delete(movieEntity);
    }

    public boolean isMovieCollected(int movieId)
    {
        UserEntity userEntity = userService.getUserFromPrincipal();
        User activeUser = User.fromEntity(userEntity);
        MovieEntity movieEntity = movieRepository.getOne(movieId);
        List<User> ownerList= Movie.movieOwnerListFromEntity(movieEntity);
        boolean isCollected = false;
        if (ownerList.stream()
                .anyMatch(owner -> owner.getId() == activeUser.getId())) {
            isCollected = true;
        } else {
            isCollected = false;
        }
        return isCollected;
    }
    @Transactional
    public List<Movie> findByMovieName(String searchText){
        List<MovieEntity> machedMovieList = movieRepository.findByNameContainingIgnoreCase(searchText);
        List<Movie> movieList = new ArrayList<>();
        machedMovieList.forEach(movieEntity -> {
            movieList.add(Movie.fromEntity(movieEntity));
        });
        return movieList;
    }

    @Transactional
    public List<Movie> findByCastName(String searchText){
        List<CastEntity> matchedCastEntity = castRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchText,searchText);
        List<Movie> movieList = new ArrayList<>();
        matchedCastEntity.forEach(movieEntity -> {
            matchedCastEntity.forEach(castEntity -> {
                movieList.add(Movie.fromEntity(castEntity.getMovieEntity()));
            });
        });
        return movieList;
    }
    @Transactional
    public List<Movie> findByCategory(MovieCategories category){
        List<MovieEntity> matchedMovieList = movieRepository.findByCategory(category);
        List<Movie> movieList = new ArrayList<>();
        matchedMovieList.forEach(movieEntity -> {
            movieList.add(Movie.fromEntity(movieEntity));
        });
        return movieList;
    }

    @Transactional
    public List<Movie> sortByDate(String type){
        List<MovieEntity> sortedMovieList;
        if(type.equals("newest")){
            sortedMovieList = movieRepository.findAllByOrderByReleaseDateDesc();
        }
        else {
            sortedMovieList = movieRepository.findAllByOrderByReleaseDateAsc();
        }
        List<Movie> movieList = new ArrayList<>();
        sortedMovieList.forEach(movieEntity -> {
            if(movieEntity.getReleaseDate() != null)
                movieList.add(Movie.fromEntity(movieEntity));
        });
        return movieList;
    }

}
