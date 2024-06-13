package com.example.Auth.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class LoggedUserManagementService {
    private String username;
}
