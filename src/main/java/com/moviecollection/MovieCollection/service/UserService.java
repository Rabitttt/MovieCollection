package com.moviecollection.MovieCollection.service;

import com.moviecollection.MovieCollection.auth.ApplicationUserDetails;
import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.auth.fakeAuthenticatedUsersDB;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.repository.UserRepository;
import com.moviecollection.MovieCollection.security.ApplicationUserRole;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //for dependency injection
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User getUserbyId(int userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        return User.fromEntity(userEntity);
    }
    @Transactional
    public boolean createNewUser(User userSignUpRequest) {
        Optional<UserEntity> optionalUser = userRepository.findByUserName(userSignUpRequest.getUserName());

        if(optionalUser.isEmpty()){
            userSignUpRequest.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
            userSignUpRequest.setRole(ApplicationUserRole.USER); //default rol is user, only admin can give admin role to any user
            userRepository.save(userSignUpRequest.toEntity());
            return true;
        }
        else{
            return false;
        }
    }
    @Transactional
    public List<Movie> getUserMovies(int id) {
       UserEntity userEntity = userRepository.getOne(id);
       List<Movie> movieList = User.userMoviesListFromEntity(userEntity);
       return movieList;
    }

    public User getUserByUsername(String username){
        System.out.println(User.fromEntity(userRepository.findByUserName(username).get()).getMovieList());
        return User.fromEntity(userRepository.findByUserName(username).get());
    }

    public ApplicationUserDetails getUserByAuthUsers(){
        return fakeAuthenticatedUsersDB.authenticatedUserlist.get(SessionManager.getSessionId());
    }
    public UserEntity getUserFromPrincipal() {
        return userRepository.findByUserName(SessionManager.getPrincipal().getUsername()).orElseThrow();
    }
}
