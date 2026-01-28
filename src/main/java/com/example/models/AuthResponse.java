package com.example.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthResponse {
    // Геттер ОБЯЗАТЕЛЬЕН
    @JsonProperty("token")  // Явно указываем имя поля в JSON
    private String token;

    // ОБЯЗАТЕЛЬНО: Пустой конструктор для Jackson
    public AuthResponse() {}

    // Конструктор с параметром
    public AuthResponse(String token) {
        this.token = token;
    }

    // Сеттер ОБЯЗАТЕЛЬЕН
    public void setToken(String token) {
        this.token = token;
    }
}