package com.example.dbankingbackend.services;

import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.entities.Customer;
import com.example.dbankingbackend.enumes.AccountStatus;
import com.example.dbankingbackend.exceptions.BalanceNotSuffisentException;
import com.example.dbankingbackend.exceptions.BankAccountNotFoundException;
import com.example.dbankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    Customer getCustomer(Long id);
    BankAccount saveCurrentBankAccount(double soldeInitiale,double overDraft ,Long customerId) throws CustomerNotFoundException;
    BankAccount saveSavingBankAccount(double soldeInitiale,double interesrRate ,Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffisentException;
    void credit(String accountId,double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffisentException;
    void transfert(String accountIdSource,String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuffisentException;

    List<BankAccount> bankAccountList();
}
