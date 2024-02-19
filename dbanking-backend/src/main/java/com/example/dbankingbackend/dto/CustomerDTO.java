package com.example.dbankingbackend.dto;

import com.example.dbankingbackend.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private Long age;
    private String mail;
    private String phoneNumber;
}
