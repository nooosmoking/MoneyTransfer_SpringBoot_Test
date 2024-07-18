package com.example.controllers;

import com.example.models.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentController {
    public ResponseEntity<Void> makePayment(@RequestBody Payment payment);
}
