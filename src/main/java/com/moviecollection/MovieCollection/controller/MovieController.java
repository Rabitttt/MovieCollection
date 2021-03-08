package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping("user/movies/create")
    public String addNewMovie(@ModelAttribute(value = "movie") Movie movie) throws Exception {
//        String sDate = movie.getReleaseDate();
//        System.out.println(movie.getReleaseDate());
//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
//        System.out.println(date);
//        System.out.println(new Timestamp(date.getTime()));

        log.info("MovieController.addNewMovie movie: {}",movie);

        movieService.createMovie(movie);
        return "redirect:/user/profile";
    }

    @GetMapping("movie/details/{id}")
    public String movieDetails(@PathVariable int id, Model movieDetails){

        Movie movie = movieService.getMovieById(id);
        log.info("MovieController.movieDetails movie: {}",movie);


        if(movie.getCreator().getUserName().equals(SessionManager.getPrincipal().getUsername())){
            movieDetails.addAttribute("movie",movie);
            return "edit-movie";
        }
        else {
//            boolean isCollected = true;

            movieDetails.addAttribute("movie",movie);
            movieDetails.addAttribute("isCollected",true);
            return "movie-details-page";
        }
    }

    @PostMapping("/movie/details/{id}")
    public String addMovieToCollection(@PathVariable int id) throws Exception {
        Movie movie = movieService.getMovieById(id);
        log.info("MovieController.addMovieToCollection movie: {}",movie);
        movieService.addMovieToCollection(movie);
        return "redirect:/login";
    }
    @PostMapping("/user/movies/edit")
    public String updateMovie(@ModelAttribute("movie")Movie editedMovie){
        log.info("MovieController.updateMovie movie: {}",editedMovie);
        editedMovie = movieService.updateMovie(editedMovie);
        log.info("MovieController.updateMovie movie: {}",editedMovie);

        return "redirect:/movie/details/" + editedMovie.getId() + "";
    }

    @InitBinder
    public void initDateBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM-dd-yyyy"), true));
    }

}
