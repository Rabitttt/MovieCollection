package com.moviecollection.MovieCollection.service;

import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //for dependency injection
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserbyId(int userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        return User.fromEntity(userEntity);
    }

    public User createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userEntity = userRepository.save(user.toEntity());
        return User.fromEntity(userEntity);
    }
}
