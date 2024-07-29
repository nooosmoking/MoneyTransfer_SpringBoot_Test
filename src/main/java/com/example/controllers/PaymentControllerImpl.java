package com.example.controllers;

import com.example.models.Payment;
import com.example.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentControllerImpl implements PaymentController {
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
}
