package com.example.dbankingbackend.repositories;

import com.example.dbankingbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankOperationRepository extends JpaRepository<AccountOperation,Long> {
}
