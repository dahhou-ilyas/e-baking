package com.example.dbankingbackend.services;

import com.example.dbankingbackend.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        //en peut appliquer des regles metier comme avant d'enregister un customer il faut le virifier par exemples s'il n'ya pas apartienne à une black liste ou d'autre chose
        log.info("save new customer");
        Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer=customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(saveCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer Not found"));
        return bankAccountMapper.fromCustomer(customer);
    }
    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double soldeInitiale, double overDraft, Long customerId) throws CustomerNotFoundException {
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
        return bankAccountMapper.fromCurrentBankAccount(saveBankeAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double soldeInitiale, double interesrRate, Long customerId) throws CustomerNotFoundException {
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
        return bankAccountMapper.fromSavingBankAccount(saveBankeAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDTO> customerDTOS= customers.stream().map(cust->bankAccountMapper.fromCustomer(cust)).toList();
        return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingBankAccount(savingAccount);
        }else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentBankAccount(currentAccount);
        }
    }
    private BankAccount getBankAccountByID(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        return bankAccount;

    }
    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccountByID(accountId);
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
        BankAccount bankAccount=getBankAccountByID(accountId);
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
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts= bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS= bankAccounts.stream().map(bankAccount -> {
            if(bankAccount instanceof SavingAccount){
                return bankAccountMapper.fromSavingBankAccount(((SavingAccount) bankAccount));
            }else {
                return bankAccountMapper.fromCurrentBankAccount(((CurrentAccount) bankAccount));
            }
        }).toList();
        return bankAccountDTOS;
    }

    @Override
    public List<AccountOperationDTO> accountHistorique(String accountId){
        List<AccountOperation> accountOperations= bankOperationRepository.findByBankAccountId(accountId);
        List<AccountOperationDTO> historique= accountOperations.stream().map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation)).toList();
        return historique;
    }

    @Override
    public AccountHistoryDTO getAccountHistorique(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=getBankAccountByID(accountId);
        Page<AccountOperation> accountOperations= bankOperationRepository.findByBankAccountId(accountId, PageRequest.of(page,size));
        // on peut faire ce traivaille dans le mapping
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS= accountOperations.getContent().stream().map(op->bankAccountMapper.fromAccountOperation(op)).toList();
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        accountHistoryDTO.setCurrentPage(page);
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.findByNameContains(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(cust -> bankAccountMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }

}
