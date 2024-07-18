package com.example.controllers.advices;

import com.example.models.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.exceptions.*;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorDetails> ExceptionNotEnoughMoneyHandler(NotEnoughMoneyException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetails> ExceptionInvalidRequestHandler(InvalidRequestException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> ExceptionIllegalArgumentHandler(IllegalArgumentException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorDetails> ExceptionNoSuchAccountException(NoSuchUserException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> ExceptionUserAlreadyExistsException(UserAlreadyExistsException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }
}
