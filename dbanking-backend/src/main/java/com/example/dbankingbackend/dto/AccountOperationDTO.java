package com.example.dbankingbackend.dto;

import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.enumes.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date oprationDate;
    private double amount;
    private OperationType type;
    private String description;
}
