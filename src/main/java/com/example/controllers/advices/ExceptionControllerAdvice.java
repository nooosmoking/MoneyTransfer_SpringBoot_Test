package com.example.controllers.advices;

import com.example.models.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.exceptions.*;


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
    public ResponseEntity<ErrorDetails> ExceptionNoSuchAccountHandler(NoSuchUserException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> ExceptionUserAlreadyExistsHandler(UserAlreadyExistsException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> ExceptionBadCredentialsHandler(BadCredentialsException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorDetails> ExceptionDatabaseHandler(DatabaseException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDetails> ExceptionValidationHandler(ValidationException ex){
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }
}
