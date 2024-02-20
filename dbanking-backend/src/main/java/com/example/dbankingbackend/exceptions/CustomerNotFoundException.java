package com.example.dbankingbackend.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
