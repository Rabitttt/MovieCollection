package com.moviecollection.MovieCollection.service;

import com.moviecollection.MovieCollection.auth.ApplicationUserDetails;
import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.auth.fakeAuthenticatedUsersDB;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.entity.UserEntity;
import com.moviecollection.MovieCollection.repository.UserRepository;
import com.moviecollection.MovieCollection.security.ApplicationUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor //for dependency injection
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserbyId(int userId) {
        UserEntity userEntity = userRepository.getOne(userId);
        return User.fromEntity(userEntity);
    }

    public boolean createNewUser(User userSignUpRequest) {
        Optional<UserEntity> optionalUser = userRepository.findByUserName(userSignUpRequest.getUserName());
        /*optionalUser.ifPresent(user -> {
            if(userSignUpRequest.getUserName().equals(user.getUserName())){
                throw new IllegalArgumentException("Username is already exists.");
            }
        });*/

        if(optionalUser.isEmpty()){
            userSignUpRequest.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
            userSignUpRequest.setRole(ApplicationUserRole.USER); //default rol is user, only admin can give admin role to any user
            userRepository.save(userSignUpRequest.toEntity());
            return true;
        }
        else{
            return false;
            //throw new IllegalArgumentException("Username is already exists.");
        }


        /*
        try { //username column is unique in db.If we try to add same username area , throw exception.
            userSignUpRequest.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
            UserEntity userEntity = userRepository.save(userSignUpRequest.toEntity());
            return User.fromEntity(userEntity);
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
*/
    }

    public User getUserByUsername(String username){
        return User.fromEntity(userRepository.findByUserName(username).get());
    }

    public ApplicationUserDetails getUserByAuthUsers(){

        return fakeAuthenticatedUsersDB.authenticatedUserlist.get(SessionManager.getSessionId());
    }
}
