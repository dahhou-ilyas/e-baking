package com.example.dbankingbackend.services;

import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.entities.Customer;
import com.example.dbankingbackend.repositories.BankAccountRepository;
import com.example.dbankingbackend.repositories.BankOperationRepository;
import com.example.dbankingbackend.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j // cette annotation utiliser pour loguer les information
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private BankOperationRepository bankOperationRepository;

    // on peut utiliser dérictement lobjet "Logger" directement sans utiliser le lombok annotatnio @Slf4j
    //Logger log=LoggerFactory.getLogger(this.getClass().getName());

    // injection de dependence
    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, BankOperationRepository bankOperationRepository) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankOperationRepository = bankOperationRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("save new customer");
        return null;
    }

    @Override
    public BankAccount saveBankAccount(double soldeInitiale, String accountStatus, Long customerId) {
        return null;
    }

    @Override
    public List<Customer> listCustomers() {
        return null;
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) {

    }

    @Override
    public void credit(String accountId, double amount, String description) {

    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) {

    }
}
