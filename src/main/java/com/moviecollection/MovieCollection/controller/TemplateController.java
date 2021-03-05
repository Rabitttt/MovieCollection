package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class TemplateController {
    private final UserService userService;


    @GetMapping("/")
    public String index(Model model){
        return "landing-page";
    }
    @GetMapping("/register")
    public String registerPage(Model model) {
        com.moviecollection.MovieCollection.domain.User user = new com.moviecollection.MovieCollection.domain.User();
        model.addAttribute("user", user);
        return "register";
    }

    /*
    @PostMapping("/register")
    public String createNewUser(@ModelAttribute(value = "user") com.moviecollection.MovieCollection.domain.User user) {
        userService.createNewUser(user);
        return "/login";
    }*/

    @PostMapping("/register")
    public String createNewUser(@ModelAttribute(value = "user") com.moviecollection.MovieCollection.domain.User user) {
        if(userService.createNewUser(user))
        {
            return "redirect:/login";
        }
        else {
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
