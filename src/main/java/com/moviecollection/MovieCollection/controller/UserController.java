package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/admin/panel")
    public String getAdminPanel(Model admin,Model appUserList){
        admin.addAttribute("admin",User.fromEntity(userService.getUserFromPrincipal()));
        appUserList.addAttribute("appUserList",userService.getAllUsers());
        return "admin-panel";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id)
    {
        userService.deleteUser(id);
        return "redirect:/";
    }

}
