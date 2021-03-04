package com.moviecollection.MovieCollection.auth;

import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
public class ApplicationUserService implements UserDetailsService {

    public UserRepository userRepository;
    @Autowired
    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> selectApplicationUserByUsername(String username){ //domain user
        return userRepository.findByUserName(username)
                .map(User::fromEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetails> userDetails = selectApplicationUserByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUserName(),
                        user.getPassword(),
                        Collections.emptyList()
                ));
        fakeAuthenticatedUsersDB.authenticatedUserlist.put(SessionManager.getSessionId(),userDetails.orElseThrow());
        return userDetails
                .orElseThrow();

    }
}
