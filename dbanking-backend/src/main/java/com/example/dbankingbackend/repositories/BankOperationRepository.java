package com.example.dbankingbackend.repositories;

import com.example.dbankingbackend.entities.AccountOperation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String accountId);

    Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);

}
