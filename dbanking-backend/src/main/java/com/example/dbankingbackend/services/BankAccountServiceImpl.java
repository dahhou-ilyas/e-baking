package com.example.dbankingbackend.services;

import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.entities.CurrentAccount;
import com.example.dbankingbackend.entities.Customer;
import com.example.dbankingbackend.entities.SavingAccount;
import com.example.dbankingbackend.enumes.AccountStatus;
import com.example.dbankingbackend.exceptions.CustomerNotFoundException;
import com.example.dbankingbackend.repositories.BankAccountRepository;
import com.example.dbankingbackend.repositories.BankOperationRepository;
import com.example.dbankingbackend.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        //en peut appliquer des regles metier comme avant d'enregister un customer il faut le virifier par exemples s'il n'ya pas apartienne à une black liste ou d'autre chose
        log.info("save new customer");
        Customer saveCustomer=customerRepository.save(customer);
        return saveCustomer;
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public BankAccount saveCurrentBankAccount(double soldeInitiale, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=getCustomer(customerId);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount=new CurrentAccount();

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(soldeInitiale);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setStatus(AccountStatus.CREATED);
        CurrentAccount saveBankeAccount=bankAccountRepository.save(currentAccount);
        return saveBankeAccount;
    }

    @Override
    public BankAccount saveSavingBankAccount(double soldeInitiale, double interesrRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=getCustomer(customerId);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount=new SavingAccount();

        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(soldeInitiale);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interesrRate);
        savingAccount.setStatus(AccountStatus.CREATED);
        SavingAccount saveBankeAccount=bankAccountRepository.save(savingAccount);
        return saveBankeAccount;
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
