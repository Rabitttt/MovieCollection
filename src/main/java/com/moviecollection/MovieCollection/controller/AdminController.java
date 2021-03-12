package com.moviecollection.MovieCollection.controller;

import com.moviecollection.MovieCollection.auth.SessionManager;
import com.moviecollection.MovieCollection.domain.User;
import com.moviecollection.MovieCollection.security.ApplicationUserRole;
import com.moviecollection.MovieCollection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/panel")
    public String getAdminPanel(Model admin, Model appUserList){
        admin.addAttribute("admin", User.fromEntity(userService.getUserFromPrincipal()));
        appUserList.addAttribute("appUserList",userService.getAllUsers());
        return "admin-panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id)
    {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
