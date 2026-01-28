package com.example.services;

import com.example.exceptions.NoSuchUserException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.models.User;
import org.springframework.security.authentication.BadCredentialsException;

import javax.security.sasl.AuthenticationException;


public interface AuthService {
    void signUp(User user) throws UserAlreadyExistsException;

    String signIn(User user) throws NoSuchUserException, BadCredentialsException;

}
