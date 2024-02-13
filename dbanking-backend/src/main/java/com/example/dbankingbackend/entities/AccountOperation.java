package com.example.dbankingbackend.entities;

import com.example.dbankingbackend.enumes.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    private Long id;
    private Date oprationDate;
    private double amount;
    private OperationType operationType;
    private BankAccount bankAccount;
}
