package com.example.controllers;

import com.example.models.User;
import com.example.models.UserChangeInfoRequest;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/signin")
    public HttpStatus signIn(@RequestBody User user) {
        userService.signIn(user);
        return HttpStatus.OK;
    }

    @Override
    @PostMapping("/signup")
    public HttpStatus signUp(@RequestBody User user) {
        userService.signUp(user);
        return HttpStatus.OK;
    }

    @Override
    @PutMapping("/users")
    public HttpStatus changeUserInfo(@RequestBody UserChangeInfoRequest userChangeInfoRequest) {
        userService.changeInfo(userChangeInfoRequest);
        return HttpStatus.OK;
    }

    @Override
    @PostMapping("/users")
    public HttpStatus addUserInfo(@RequestBody UserChangeInfoRequest userChangeInfoRequest) {
        userService.addInfo(userChangeInfoRequest);
        return HttpStatus.OK;
    }

    @Override
    @DeleteMapping("/users")
    public HttpStatus deleteUserInfo(@RequestBody UserChangeInfoRequest userChangeInfoRequest) {
        userService.deleteInfo(userChangeInfoRequest);
        return HttpStatus.OK;
    }
}
