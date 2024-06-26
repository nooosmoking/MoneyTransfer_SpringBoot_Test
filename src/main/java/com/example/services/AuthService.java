package com.example.services;

import com.example.exceptions.NoSuchUserException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.models.User;

import javax.security.sasl.AuthenticationException;


public interface AuthService {
    String signUp(User user) throws UserAlreadyExistsException;

    String signIn(User user) throws NoSuchUserException, AuthenticationException;

}
