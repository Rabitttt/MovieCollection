package com.moviecollection.MovieCollection.repository;

import com.moviecollection.MovieCollection.entity.CastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastRepository extends JpaRepository<CastEntity,Integer> {
    List<CastEntity> findByFirstNameLike(String searchText);

}
