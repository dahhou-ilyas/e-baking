package com.example.dbankingbackend.web;

import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;


}
