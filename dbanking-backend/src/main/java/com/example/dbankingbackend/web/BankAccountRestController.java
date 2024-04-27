package com.example.dbankingbackend.web;

import com.example.dbankingbackend.dto.*;
import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.exceptions.BalanceNotSuffisentException;
import com.example.dbankingbackend.exceptions.BankAccountNotFoundException;
import com.example.dbankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }


    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistorique(@PathVariable String accountId){
        return bankAccountService.accountHistorique(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistorique(@PathVariable String accountId, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
         return bankAccountService.getAccountHistorique(accountId,page,size);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSuffisentException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSuffisentException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

}
