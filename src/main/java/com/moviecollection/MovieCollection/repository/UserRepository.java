package com.moviecollection.MovieCollection.repository;

import com.moviecollection.MovieCollection.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUserName(String userName);
}

