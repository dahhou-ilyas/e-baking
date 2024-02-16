package com.example.dbankingbackend.exceptions;

public class BalanceNotSuffisentException extends Exception {
    public BalanceNotSuffisentException(String message) {
        super(message);
    }
}
