package com.example.Auth.controllers;

import com.example.Auth.processors.AuthProcessor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class AuthController {
    private final AuthProcessor authProcessor;

    @GetMapping("/login")
    public String loginGet(){
        return "login.html";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password, Model page){
        authProcessor.setUsername(username);
        authProcessor.setPassword(password);
        boolean isLoggedIn = authProcessor.login();
        if (isLoggedIn){
            return "redirect:/home";
        }
        page.addAttribute("message",  "Login failed!");
        return "login.html";
    }
}
