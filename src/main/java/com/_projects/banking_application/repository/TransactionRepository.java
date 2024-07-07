package com._projects.banking_application.repository;

import com._projects.banking_application.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByAccountIdOrderByTimestampDesc(Long id);
}
