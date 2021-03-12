package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/profile")
    public String getUserProfile(Model userMovies, Principal principal, Model model, @ModelAttribute(value = "movie") Movie movie)
    {
        User user = userService.getUserByUsername(SessionManager.getPrincipal().getUsername());

        List<Movie> movieList = userService.getUserMovies(user.getId());

        userMovies.addAttribute("userMovies",movieList);
        model.addAttribute("user",user);

        return "user-profile";
    }

    @GetMapping("/details/{id}")
    public String getUserDetails(@PathVariable("id") int userId,Model userMovies, Model model){
        userMovies.addAttribute("userMovies",userService.getUserMovies(userId));
        model.addAttribute("user",userService.getUserbyId(userId));
        return "user-details";
    }

}
