package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping()
@RequiredArgsConstructor
public class MovieController {

    @PostMapping("/user/profile")
    public String addNewMovie(@ModelAttribute(value = "movie") Movie movie, Principal principal){
        System.out.println(movie);
        return "redirect:/user/profile";
    }
}
