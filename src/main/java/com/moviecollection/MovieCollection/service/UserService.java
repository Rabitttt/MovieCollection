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


import java.util.ArrayList;
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

            if(userSignUpRequest.getUserName().equals("admin"))
                userSignUpRequest.setRole(ApplicationUserRole.ADMIN); //if username is admin set role to admin
            else
                userSignUpRequest.setRole(ApplicationUserRole.USER);//default rol is user
            userRepository.save(userSignUpRequest.toEntity());
            return true;
        }
        else{
            return false;
        }
    }
    @Transactional
    public List<User> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        userEntities.forEach(userEntity -> {
            userList.add(User.fromEntity(userEntity));
        });
        return userList;
    }

    @Transactional
    public void deleteUser(int userId){
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        userEntity.getOwnedMovies().removeIf(movieEntity -> movieEntity.getCreator().getId() != userEntity.getId());
        userRepository.delete(userEntity);
    }

    @Transactional
    public List<Movie> getUserMovies(int userId) {
       UserEntity userEntity = userRepository.getOne(userId);
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
