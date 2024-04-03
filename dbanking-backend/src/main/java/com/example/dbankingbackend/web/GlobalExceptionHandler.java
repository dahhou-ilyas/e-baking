package com.example.dbankingbackend.web;

import com.example.dbankingbackend.exceptions.BalanceNotSuffisentException;
import com.example.dbankingbackend.exceptions.BankAccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<String> handleBankAccountNotFoundException(BankAccountNotFoundException exception){
        return new ResponseEntity<>("Bank account not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BalanceNotSuffisentException.class)
    public ResponseEntity<String> handleBalanceNotSufficientException(BalanceNotSuffisentException ex) {
        return new ResponseEntity<>("Insufficient balance", HttpStatus.BAD_REQUEST);
    }
}
