package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.ApplicationUserDetails;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{id}")
    @ModelAttribute("user")
    public User getUserByID(@PathVariable("id") int id) {
         return userService.getUserbyId(id);//deneme.html
    }

    @GetMapping("/profile")
    public String getUserProfile(Model userMovies, Principal principal, Model model, @ModelAttribute(value = "movie") Movie movie)
    {
        List<Movie> movieList = new ArrayList<>();
        movieList = userService.getUserMovies(Integer.parseInt(userService.getUserByAuthUsers().getId()));
        userMovies.addAttribute("userMovies",movieList);
        ApplicationUserDetails applicationUserDetails = userService.getUserByAuthUsers();

        model.addAttribute("user",applicationUserDetails);

        return "user-profile";
    }
}
