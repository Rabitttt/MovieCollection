package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "{id}")
    public User getUserByID(@PathVariable("userId") int userId) {
        return userService.getUserbyId(userId);
    }

    @PostMapping
    public User createNewUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }
}
