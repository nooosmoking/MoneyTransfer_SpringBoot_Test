package com.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private long id;
    private String login;
    private String password;
    private double balance;
    private String fullName;
    private Date birthday;
    private List<String> phoneNumbers;
    private List<String> emails;

    public User(String login, String password, double balance, String fullName, Date birthday, String phoneNumber, String email) {
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.fullName = fullName;
        this.birthday = birthday;
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
