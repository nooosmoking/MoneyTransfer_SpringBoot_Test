package com.example.controllers;

import com.example.models.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentController {
    HttpStatus makePayment(@RequestBody Payment payment);
}
