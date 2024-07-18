package com.example.controllers;

import com.example.exceptions.NotEnoughMoneyException;
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
    public ResponseEntity<Void> makePayment(@RequestBody Payment payment) {
        paymentService.makePayment(payment);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
//
//    @PutMapping("/payments")
//    public ResponseEntity<Payment> changePayment(@RequestBody Payment payment){
//        paymentService.changePayment(payment);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(payment);
//    }
//
//
//    @DeleteMapping("/payments/{id}")
//    public ResponseEntity<Void> deletePayment(@PathVariable int id){
//        paymentService.deletePayment(id);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//    }
}
