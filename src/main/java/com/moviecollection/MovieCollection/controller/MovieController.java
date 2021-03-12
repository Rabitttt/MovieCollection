package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.domain.Cast;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.enums.MovieCategories;
import com.moviecollection.MovieCollection.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
        System.out.println("Tarih: " +movie.getReleaseDate());
        movieService.createMovie(movie);
        return "redirect:/user/profile";
    }
    @GetMapping("movie/details/{id}")
    public String movieDetails(@PathVariable int id, Model movieDetails, Model model,Model castList,Model ownerList,Model creator){
        Movie movie = movieService.getMovieById(id);
        log.info("MovieController.movieDetails Movie movie: {}",movie);
        castList.addAttribute("castList",movieService.getMovieCastList(id));
        ownerList.addAttribute("ownerList",movieService.getMovieOwners(id));
        creator.addAttribute("creator",movie.getCreator());

        if(movie.getCreator().getUserName().equals(SessionManager.getPrincipal().getUsername())){
            movieDetails.addAttribute("movie",movie);
            model.addAttribute("newCast",new Cast());
            return "edit-movie";
        }
        else {
            boolean isCollected = movieService.isMovieCollected(id);
            movieDetails.addAttribute("movie",movie);
            movieDetails.addAttribute("isCollected",isCollected);
            return "movie-details-page";
        }
    }

    @PostMapping("/movie/details/{id}")
    public String addMovieToCollection(@PathVariable int id) throws Exception {
        Movie movie = movieService.getMovieById(id);
        log.info("MovieController.addMovieToCollection movie: {}",movie);
        movieService.addMovieToCollection(movie);
        return "redirect:/movie/details/" + id + "";
    }
    @PostMapping("/user/movies/edit")
    public String updateMovie(@ModelAttribute("movie")Movie editedMovie){
        log.info("MovieController.updateMovie movie: {}",editedMovie);
        editedMovie = movieService.updateMovie(editedMovie);
        log.info("MovieController.updateMovie movie: {}",editedMovie);

        return "redirect:/movie/details/" + editedMovie.getId() + "";
    }

    @PostMapping("user/movies/{id}/newCast")
    public String addCast(@PathVariable int id ,@ModelAttribute("newCast") Cast newCast){
        movieService.createCastForMovie(newCast,id);
        return "redirect:/movie/details/" + id + "";
    }

    @PostMapping("/user/movies/{id}/remove/from/collection")
    public String removeFromCollection(@PathVariable int id){
        movieService.removeMovieFromCollection(id);
        return "redirect:/movie/details/" + id + "";
    }

    @PostMapping("/user/movies/{id}/delete")
    public String deleteMovie(@PathVariable int id){
        movieService.deleteMovie(id);
        return "redirect:/user/profile";
    }

    @PostMapping("/user/movies/cast/{castId}/delete")
    public String deleteCast(@PathVariable int castId){
        int movieId = movieService.deleteCastFromMovie(castId);
        return "redirect:/movie/details/" + movieId + "";
    }

    @GetMapping("/search/by/movie/name")
    public String searchByMovieName(@RequestParam("name") String searchText,Model movieList) {
        movieList.addAttribute("movieList",movieService.findByMovieName(searchText));
        return "landing-page";
    }

    @GetMapping("/search/by/movie/cast")
    public String searchByMovieCast(@RequestParam("cast") String searchText,Model movieList) {
        movieList.addAttribute("movieList", movieService.findByCastName(searchText));
        return "landing-page";
    }

    @GetMapping("/search/by/movie/category")
    public String searchByMovieCategory(@RequestParam("category") MovieCategories category, Model movieList) {
        movieList.addAttribute("movieList", movieService.findByCategory(category));
        return "landing-page";
    }
    @GetMapping("/sort/movie/by/releaseDate")
    public String searchByMovieReleaseDate(@RequestParam("releaseDate") String sortType,Model movieList) {
        movieList.addAttribute("movieList", movieService.sortByDate(sortType));
        System.out.println("sortType :" + sortType);
        return "landing-page";
    }

    //time formatter
    @InitBinder
    public void initDateBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }


}
