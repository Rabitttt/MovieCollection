package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.domain.Cast;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.enums.MovieCategories;
import com.moviecollection.MovieCollection.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
        log.info("MovieController.addNewMovie movie: {}",movie);
        movieService.createMovie(movie);
        return "redirect:/user/profile";
    }

    @GetMapping("movie/details/{id}")
    public String movieDetails(@PathVariable int id, Model movieDetails, Model model){
        Movie movie = movieService.getMovieById(id);
//        castList.addAttribute("castList",movieService.movieCastList(movie));
        log.info("MovieController.movieDetails movie: {}",movie);


        if(movie.getCreator().getUserName().equals(SessionManager.getPrincipal().getUsername())){
            movieDetails.addAttribute("movie",movie);
            model.addAttribute("newCast",new Cast());

//            cast.addAttribute("newCast");
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

    @PostMapping("user/details/{id}/newCast")
    public String addCast(@PathVariable int id ,@ModelAttribute("newCast") Cast newCast){
        movieService.createCastForMovie(newCast,id);
        return "redirect:/";
    }


    @GetMapping("/serch/by/movie/name")
    public String searchByMovieName(@RequestParam("name") String searchText,Model movieList) {
        movieList.addAttribute("movieList",movieService.findByMovieName(searchText));
        return "landing-page";
    }

    @GetMapping("/serch/by/movie/cast")
    public String searchByMovieCast(@RequestParam("cast") String searchText,Model movieList) {
        movieList.addAttribute("movieList", movieService.findByCastName(searchText));
        return "landing-page";
    }

    @GetMapping("/serch/by/movie/category")
    public String searchByMovieCategory(@RequestParam("category") MovieCategories category, Model movieList) {
        movieList.addAttribute("movieList", movieService.findByCategory(category));
        return "landing-page";
    }
    @GetMapping("/sort/by/movie/releaseDate")
    public String searchByMovieCategory(Model movieList) {
        movieList.addAttribute("movieList", movieService.sortByDate());
        return "landing-page";
    }

    //time formatter
    @InitBinder
    public void initDateBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM-dd-yyyy"), true));
    }

}
