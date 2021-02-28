package com.moviecollection.MovieCollection.repository;

import com.moviecollection.MovieCollection.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity,Integer> {
}
