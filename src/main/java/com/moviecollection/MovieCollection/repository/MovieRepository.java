package com.moviecollection.MovieCollection.repository;

import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.entity.MovieEntity;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.enums.MovieCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Integer> {
    List<MovieEntity> findByNameContainingIgnoreCase(String searchText);
    List<MovieEntity> findByCategory(MovieCategories category);
    List<MovieEntity> findAllByOrderByReleaseDateAsc();
    List<MovieEntity> findAllByOrderByReleaseDateDesc();
}

