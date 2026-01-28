package com.example.services;

import com.example.exceptions.NoSuchUserException;
import com.example.exceptions.NotEnoughMoneyException;
import com.example.models.Payment;

public interface PaymentService {
    void makePayment(Payment payment) throws NoSuchUserException, NotEnoughMoneyException, IllegalArgumentException;
}
