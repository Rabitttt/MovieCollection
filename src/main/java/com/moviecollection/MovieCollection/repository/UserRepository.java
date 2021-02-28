package com.moviecollection.MovieCollection.repository;

import com.moviecollection.MovieCollection.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
}
