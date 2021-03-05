package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.ApplicationUserDetails;
import com.moviecollection.MovieCollection.domain.Movie;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.security.Principal;

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
    public String getUserProfile(Principal principal, Model model,@ModelAttribute(value = "movie") Movie movie)
    {
        /*
        User user = userService.getUserByUsername(principal.getName());
        session.setAttribute("user",user);
        return "user-profile";
        */

        ApplicationUserDetails applicationUserDetails = userService.getUserByAuthUsers();

        model.addAttribute("user",applicationUserDetails);

        System.out.println(applicationUserDetails.getGender().name());
        return "user-profile";
        //return String.format("redirect:/user/%s",user.getId());
    }
}
