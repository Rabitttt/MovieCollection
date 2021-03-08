package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.ApplicationUserDetails;
import com.moviecollection.MovieCollection.auth.SessionManager;
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
        User user = userService.getUserByUsername(SessionManager.getPrincipal().getUsername());

        List<Movie> movieList = userService.getUserMovies(user.getId());

        userMovies.addAttribute("userMovies",movieList);
        model.addAttribute("user",user);

//        ApplicationUserDetails applicationUserDetails = userService.getUserByAuthUsers();
//        List<Movie> movieList = userService.getUserMovies(principal.getName());
//
//        userMovies.addAttribute("userMovies",movieList);
//        model.addAttribute("user",applicationUserDetails);
//
        return "user-profile";
    }
}
