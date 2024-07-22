package com.example.controllers;

import com.example.models.Payment;
import com.example.models.User;
import com.example.models.UserChangeInfoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentController {
    HttpStatus makePayment(@RequestBody Payment payment);

    HttpStatus signIn(@RequestBody User user);

    HttpStatus signUp(@RequestBody User user);

    HttpStatus changeUserInfo(@RequestBody UserChangeInfoRequest userChangeInfoRequest);

    HttpStatus addUserInfo(@RequestBody UserChangeInfoRequest userChangeInfoRequest);

    HttpStatus deleteUserInfo(@RequestBody UserChangeInfoRequest userChangeInfoRequest);
}
