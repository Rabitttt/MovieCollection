package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/user/movies/create")
    public String addNewMovie(@ModelAttribute(value = "movie") Movie movie) throws Exception {
//        String sDate = movie.getReleaseDate();
//        System.out.println(movie.getReleaseDate());
//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
//        System.out.println(date);
//        System.out.println(new Timestamp(date.getTime()));


        movieService.createMovie(movie);
        System.out.println(movie);
        return "redirect:/user/profile";
    }

    @InitBinder
    public void initDateBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM-dd-yyyy"), true));
    }

}
