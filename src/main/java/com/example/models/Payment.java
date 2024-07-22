package com.example.models;

import lombok.Data;

@Data
public class Payment {
    private int amount;
    private String receiver;

    public Payment(int amount, String receiver) {
        this.amount = amount;
        this.receiver = receiver;
    }

}
