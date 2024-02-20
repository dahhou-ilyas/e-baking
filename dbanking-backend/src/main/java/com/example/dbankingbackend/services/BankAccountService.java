package com.example.dbankingbackend.services;

import com.example.dbankingbackend.dto.BankAccountDTO;
import com.example.dbankingbackend.dto.CurrentBankAccountDTO;
import com.example.dbankingbackend.dto.CustomerDTO;
import com.example.dbankingbackend.dto.SavingBankAccountDTO;
import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.entities.Customer;
import com.example.dbankingbackend.enumes.AccountStatus;
import com.example.dbankingbackend.exceptions.BalanceNotSuffisentException;
import com.example.dbankingbackend.exceptions.BankAccountNotFoundException;
import com.example.dbankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;
    CurrentBankAccountDTO saveCurrentBankAccount(double soldeInitiale, double overDraft , Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double soldeInitiale, double interesrRate , Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffisentException;
    void credit(String accountId,double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffisentException;
    void transfert(String accountIdSource,String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuffisentException;

    List<BankAccountDTO> bankAccountList();
}
