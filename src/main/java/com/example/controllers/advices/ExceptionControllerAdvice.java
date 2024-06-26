package com.example.controllers.advices;

import com.example.models.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.exceptions.*;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorDetails> ExceptionNotEnoughMoneyHandler(){
        ErrorDetails errorDetails = new ErrorDetails("User does not have enough funds to complete this transaction.");
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorDetails> ExceptionNoSuchAccountException(NoSuchUserException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }
}
