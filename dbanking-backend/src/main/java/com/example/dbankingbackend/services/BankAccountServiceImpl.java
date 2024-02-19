package com.example.dbankingbackend.services;

import com.example.dbankingbackend.dto.CustomerDTO;
import com.example.dbankingbackend.entities.*;
import com.example.dbankingbackend.enumes.AccountStatus;
import com.example.dbankingbackend.enumes.OperationType;
import com.example.dbankingbackend.exceptions.BalanceNotSuffisentException;
import com.example.dbankingbackend.exceptions.BankAccountNotFoundException;
import com.example.dbankingbackend.exceptions.CustomerNotFoundException;
import com.example.dbankingbackend.mappers.BankAccountMapperImpl;
import com.example.dbankingbackend.repositories.BankAccountRepository;
import com.example.dbankingbackend.repositories.BankOperationRepository;
import com.example.dbankingbackend.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
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
    private BankAccountMapperImpl bankAccountMapper;

    // on peut utiliser dérictement lobjet "Logger" directement sans utiliser le lombok annotatnio @Slf4j
    //Logger log=LoggerFactory.getLogger(this.getClass().getName());

    // injection de dependence
    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, BankOperationRepository bankOperationRepository,BankAccountMapperImpl bankAccountMapper) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankOperationRepository = bankOperationRepository;
        this.bankAccountMapper=bankAccountMapper;
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        //en peut appliquer des regles metier comme avant d'enregister un customer il faut le virifier par exemples s'il n'ya pas apartienne à une black liste ou d'autre chose
        log.info("save new customer");
        Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer=customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(saveCustomer);
    }
    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer Not found"));
        return bankAccountMapper.fromCustomer(customer);
    }
    @Override
    public BankAccount saveCurrentBankAccount(double soldeInitiale, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
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
        Customer customer=customerRepository.findById(customerId).orElse(null);
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
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDTO> customerDTOS= customers.stream().map(cust->bankAccountMapper.fromCustomer(cust)).toList();
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));

        return bankAccount;
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);
        System.out.println(bankAccount);
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOprationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        bankOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffisentException {
        BankAccount bankAccount=getBankAccount(accountId);
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSuffisentException("Balance not sufficient");
        }
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOprationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        bankOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuffisentException {
        debit(accountIdSource,amount,"transfert to "+accountIdDestination);
        credit(accountIdDestination,amount,"transfert to "+accountIdSource);
    }

    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }
}
