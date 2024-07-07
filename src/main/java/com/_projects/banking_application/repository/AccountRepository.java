package com._projects.banking_application.repository;

import com._projects.banking_application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
