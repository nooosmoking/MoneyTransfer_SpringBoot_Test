package com.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Transfer {
    private long id;

    private BigDecimal amount;

    private User sender;


    private User receiver;

    private LocalDateTime timestamp;

    public Transfer(User sender, User receiver, BigDecimal amount) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = LocalDateTime.now();
    }
}