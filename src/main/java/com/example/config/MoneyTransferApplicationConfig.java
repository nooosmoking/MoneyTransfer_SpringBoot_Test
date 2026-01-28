package com.example.config;

import org.springframework.context.annotation.*;

import org.springframework.stereotype.Component;

@PropertySource("classpath:application.properties")
@Component
public class MoneyTransferApplicationConfig {

}
