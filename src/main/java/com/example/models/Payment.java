package com.example.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private BigDecimal amount;
    private String receiver;

    public Payment(BigDecimal amount, String receiver) {
        this.amount = amount;
        this.receiver = receiver;
    }

}
