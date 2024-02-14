package com.example.dbankingbackend;

import com.example.dbankingbackend.entities.*;
import com.example.dbankingbackend.enumes.AccountStatus;
import com.example.dbankingbackend.enumes.OperationType;
import com.example.dbankingbackend.repositories.BankAccountRepository;
import com.example.dbankingbackend.repositories.BankOperationRepository;
import com.example.dbankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DbankingBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbankingBackendApplication.class, args);
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, BankOperationRepository bankOperationRepository){
        return args -> {
            Stream.of("hassan","Yassir","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setAge(40L);
                customer.setMail(name+"@gmail.com");
                customer.setPhoneNumber("0612345678");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*100000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*100000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(4.6);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i <10 ; i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOprationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    bankOperationRepository.save(accountOperation);
                }

            });
            //hibernit connue automatiquement que si ce account et savingAccoutn ou currentAccount ; meme que on a une seul table dans la base de donné "backAccount"


        };
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
        return args -> {
            BankAccount bankAccount=bankAccountRepository.findById("19dd79a3-5521-4379-b2b3-c8e9729e6a8e").orElse(null);
            if(bankAccount != null){
                System.out.println("***************");
                System.out.println(bankAccount.getId());
                System.out.println(bankAccount.getBalance());
                System.out.println(bankAccount.getCreatedAt());
                System.out.println(bankAccount.getCustomer().getName());
                if (bankAccount instanceof CurrentAccount){
                    System.out.println(((CurrentAccount)bankAccount).getOverDraft());
                }else if(bankAccount instanceof SavingAccount){
                    System.out.println(((SavingAccount)bankAccount).getInterestRate());
                }
            }
        };
    }

}
