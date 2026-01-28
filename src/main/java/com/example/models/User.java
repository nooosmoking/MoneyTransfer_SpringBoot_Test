package com.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String login;
    private String password;
    private BigDecimal balance;
    private List<String> phoneNumbers;
    private List<String> emails;

    public User(String login, String password, BigDecimal balance, String phoneNumber, String email) {
        this.login = login;
        this.password = password;
        this.balance = balance;

        this.phoneNumbers = new LinkedList<>();
        phoneNumbers.add(phoneNumber);
        this.emails = new LinkedList<>();
        emails.add(email);
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
