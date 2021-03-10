package com.moviecollection.MovieCollection.repository;

import com.moviecollection.MovieCollection.entity.CastEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastRepository extends JpaRepository<CastEntity,Integer> {
    List<CastEntity> findByFirstNameLike(String searchText);

    @Query("SELECT cast FROM CastEntity cast INNER JOIN MovieEntity movie ON cast.movieEntity = movie.id WHERE cast.movieEntity.id = :requestedMovieId")
    List<CastEntity> findAllByMovieEntity(@Param("requestedMovieId") int requestedMovieId);


}
