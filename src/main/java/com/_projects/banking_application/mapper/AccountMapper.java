package com._projects.banking_application.mapper;

import com._projects.banking_application.dto.AccountDTO;
import com._projects.banking_application.entity.Account;

public class AccountMapper {
//    DTO to JPA
    public  static Account mapToAccount(AccountDTO accountDTO){

        Account account = new Account();
        if (accountDTO.id() != null) {
            account.setAccountID(accountDTO.id());
        }
        account.setAccountHolderName(accountDTO.accountHolderName());
        account.setBalance(accountDTO.balance());
        return account;  
    }
//    JPA to DTO
    public  static AccountDTO mapToAccountDTO(Account account){
        AccountDTO accountDTO = new AccountDTO(
                account.getAccountID(),
                account.getAccountHolderName(),
                account.getBalance()
        );
//        AccountDTO accountDTO = new AccountDTO();
//        accountDTO.id(account.getAccountID());
//        accountDTO.setAccountHolderName(account.getAccountHolderName());
//        accountDTO.setBalance(account.getBalance());
        return accountDTO;
    }
}
