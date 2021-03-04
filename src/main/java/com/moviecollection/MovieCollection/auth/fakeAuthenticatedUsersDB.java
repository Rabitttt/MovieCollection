package com.moviecollection.MovieCollection.auth;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public class fakeAuthenticatedUsersDB {
    public static HashMap<String, UserDetails> authenticatedUserlist = new HashMap<>();
}
