package com.example.dbankingbackend;

import com.example.dbankingbackend.dto.CustomerDTO;
import com.example.dbankingbackend.entities.*;
import com.example.dbankingbackend.enumes.AccountStatus;
import com.example.dbankingbackend.enumes.OperationType;
import com.example.dbankingbackend.exceptions.BalanceNotSuffisentException;
import com.example.dbankingbackend.exceptions.BankAccountNotFoundException;
import com.example.dbankingbackend.exceptions.CustomerNotFoundException;
import com.example.dbankingbackend.repositories.BankAccountRepository;
import com.example.dbankingbackend.repositories.BankOperationRepository;
import com.example.dbankingbackend.repositories.CustomerRepository;
import com.example.dbankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DbankingBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbankingBackendApplication.class, args);
    }

    //tester la couche service
    //@Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan","Iman","Mohamed").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setAge(20L);
                customer.setMail(name+"@gmail.com");
                customer.setPhoneNumber("0666162326");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(cust->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*900000,9000, cust.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*1200000,5.1,cust.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccount> bankAccountList= bankAccountService.bankAccountList();
            for (BankAccount account:bankAccountList){
                for (int i=0;i<10;i++){
                    bankAccountService.credit(account.getId(),1000*Math.random()*120000,"Credit");
                    bankAccountService.debit(account.getId(),1000*Math.random()*1000,"Debit");
                }
            }
        };
    }

}
