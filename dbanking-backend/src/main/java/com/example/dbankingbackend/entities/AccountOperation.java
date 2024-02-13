package com.example.dbankingbackend.entities;

import com.example.dbankingbackend.enumes.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date oprationDate;
    private double amount;
    private OperationType operationType;
    @ManyToOne
    private BankAccount bankAccount;
}
