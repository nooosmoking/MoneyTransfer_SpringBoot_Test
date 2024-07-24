package com.example.controllers;

import com.example.exceptions.NotEnoughMoneyException;
import com.example.models.User;
import com.example.models.UserChangeInfoRequest;
import com.example.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.models.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PaymentControllerImpl implements PaymentController{
    private final PaymentService paymentService;

    @Autowired
    public PaymentControllerImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public HttpStatus makePayment(@RequestBody Payment payment) {
        paymentService.makePayment(payment);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus signIn(User user) {
        return null;
    }

    @Override
    public HttpStatus signUp(User user) {
        return null;
    }

    @Override
    public HttpStatus changeUserInfo(UserChangeInfoRequest userChangeInfoRequest) {
        return null;
    }

    @Override
    public HttpStatus addUserInfo(UserChangeInfoRequest userChangeInfoRequest) {
        return null;
    }

    @Override
    public HttpStatus deleteUserInfo(UserChangeInfoRequest userChangeInfoRequest) {
        return null;
    }
}
