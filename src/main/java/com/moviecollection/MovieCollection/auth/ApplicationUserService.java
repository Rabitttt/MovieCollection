package com.moviecollection.MovieCollection.auth;

import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        if (selectApplicationUserByUsername(username).isPresent())
        {
            ApplicationUserDetails applicationUserDetails =
                    ApplicationUserDetails.fromUser(selectApplicationUserByUsername(username).get());
                    fakeAuthenticatedUsersDB.authenticatedUserlist.put(SessionManager.getSessionId(),applicationUserDetails);
            System.out.println();
                    return applicationUserDetails;
        }
        else {
            return null;
        }

    }
}
