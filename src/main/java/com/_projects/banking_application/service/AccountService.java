package com._projects.banking_application.service;

import com._projects.banking_application.dto.AccountDTO;
import com._projects.banking_application.dto.TransactionDTO;

import com._projects.banking_application.dto.TransferFundDTO;

import java.util.List;
//import com._projects.banking_application.entity.Account;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO getAccountByID(Long accountID);

    AccountDTO depositAmount(Long accountID, double amount);

    AccountDTO withdrawAmount(Long accountID, double amount);

    List<AccountDTO> getAllAccounts();

    AccountDTO deleteAccount(Long accountID);

    void transferFund(TransferFundDTO transferFundDTO);


    List<TransactionDTO> getAllTransactionsById(Long id);

}
