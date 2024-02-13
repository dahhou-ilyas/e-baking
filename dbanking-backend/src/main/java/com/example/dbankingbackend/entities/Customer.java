package com.example.dbankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private Long age;
    private String mail;
    private String phoneNumber;
    private List<BankAccount> bankAccounts;
}
