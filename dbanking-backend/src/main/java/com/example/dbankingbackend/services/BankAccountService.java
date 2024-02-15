package com.example.dbankingbackend.services;

import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.entities.Customer;
import com.example.dbankingbackend.enumes.AccountStatus;
import com.example.dbankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);

    Customer getCustomer(Long id);
    BankAccount saveBankAccount(double soldeInitiale, String type,Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId,double amount, String description);
    void credit(String accountId,double amount, String description);
    void transfert(String accountIdSource,String accountIdDestination, double amount);
}
