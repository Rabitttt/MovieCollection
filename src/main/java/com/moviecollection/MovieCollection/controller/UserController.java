package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{id}")
    public User getUserByID(@PathVariable("id") int id) {
        userService.getUserbyId(id); //veri direk template a gidecek, controller sadece url gibi kullanılacak
        return new User();//deneme.html
    }

}
