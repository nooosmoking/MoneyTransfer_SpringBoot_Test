package com.example.processors;

import com.example.services.LoggedUserManagementService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class AuthProcessor {
    private final LoggedUserManagementService loggedUserManagementService;

    private String username;
    private String password;

    public void login(){
            loggedUserManagementService.setUsername(username);
    }
}
