package com.example.Auth.processors;

import com.example.Auth.services.LoggedUserManagementService;
import com.example.Auth.services.LoginCountService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class AuthProcessor {
    private final LoggedUserManagementService loggedUserManagementService;
    private final LoginCountService loginCountService;

    private String username;
    private String password;

    public boolean login(){
        loginCountService.increment();
        boolean result = username.equals("annette")&&password.equals("password");
        if (result){
            loggedUserManagementService.setUsername(username);
        }
        return result;
    }
}
