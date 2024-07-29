package com.example.services;

import com.example.exceptions.NoSuchUserException;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.models.User;
import com.example.models.UserChangeInfoRequest;

public interface UserService {
    void signUp(User user) throws UserAlreadyExistsException;

    void signIn(User user) throws NoSuchUserException;

    void changeInfo(UserChangeInfoRequest userChangeInfoRequest);

    void deleteInfo(UserChangeInfoRequest userChangeInfoRequest);

    void addInfo(UserChangeInfoRequest userChangeInfoRequest);
}
