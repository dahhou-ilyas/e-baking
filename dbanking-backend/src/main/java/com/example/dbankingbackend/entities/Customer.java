package com.example.dbankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long age;
    private String mail;
    private String phoneNumber;
    @OneToMany(mappedBy = "customer")
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) permet de ignorer cette attribue lors de la serialisation pour la lecture
    private List<BankAccount> bankAccounts;
}
