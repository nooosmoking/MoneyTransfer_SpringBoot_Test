package com.example.Auth.controllers;

import com.example.Auth.services.LoggedUserManagementService;
import com.example.Auth.services.LoginCountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MainController {
    private final LoggedUserManagementService loggedUserManagementService;
    private final LoginCountService loginCountService;

    @GetMapping("/")
    public String redirectMain(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home(@RequestParam(required = false) String Logout, Model page){
        if(Logout != null){
            loggedUserManagementService.setUsername(null);
        }
        String username = loggedUserManagementService.getUsername();
        if(username==null){
            return "redirect:/login";
        }
        page.addAttribute("username",username);
        page.addAttribute("count", loginCountService.getCount());
        return "main.html";
    }
}
