package com.example.dbankingbackend.web;

import com.example.dbankingbackend.dto.AccountHistoryDTO;
import com.example.dbankingbackend.dto.AccountOperationDTO;
import com.example.dbankingbackend.dto.BankAccountDTO;
import com.example.dbankingbackend.entities.BankAccount;
import com.example.dbankingbackend.exceptions.BankAccountNotFoundException;
import com.example.dbankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
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

}
