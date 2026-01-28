package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(
		scanBasePackages = "com.example",
		exclude = {UserDetailsServiceAutoConfiguration.class})
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}