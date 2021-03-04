package com.moviecollection.MovieCollection.repository;

import com.moviecollection.MovieCollection.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUserName(String userName);
}

