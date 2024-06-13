package com.example.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SigninRequest extends Request {
    private String password;

    public SigninRequest(String json) throws JsonProcessingException {
        super();
        ObjectMapper mapper = new ObjectMapper();
        SigninRequest signinRequest = mapper.readValue(json, SigninRequest.class);
        this.setLogin(signinRequest.getLogin());
        this.password = signinRequest.getPassword();
    }
}
