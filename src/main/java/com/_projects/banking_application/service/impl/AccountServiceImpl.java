package com._projects.banking_application.service.impl;

import com._projects.banking_application.dto.AccountDTO;
import com._projects.banking_application.dto.TransactionDTO;
import com._projects.banking_application.dto.TransferFundDTO;
import com._projects.banking_application.entity.Account;
import com._projects.banking_application.entity.Transactions;
import com._projects.banking_application.exception.AccountException;
import com._projects.banking_application.mapper.AccountMapper;
import com._projects.banking_application.repository.AccountRepository;
import com._projects.banking_application.repository.TransactionRepository;
import com._projects.banking_application.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private static final String TRANSACTION_TYPE_DEPOSIT = "Deposit";
    private static final String TRANSACTION_TYPE_WITHDRAW = "Withdraw";
    private static final String TRANSACTION_TYPE_TRANSFER_SENT = "Transfer_Sent";
    private static final String TRANSACTION_TYPE_TRANSFER_RECEIVED = "Transfer_Received";

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountMapper.mapToAccount(accountDTO);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO getAccountByID(Long accountID) {
        Account account = accountRepository.findById(accountID).orElseThrow(() -> new AccountException("Account does not exists."));
        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO depositAmount(Long accountID, double amount) {
        Account account = accountRepository.findById(accountID).orElseThrow(() -> new AccountException("Account does not exists."));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transactions transaction = new Transactions();
        transaction.setAccountId(accountID);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO withdrawAmount(Long accountID, double amount) {
        Account account = accountRepository.findById(accountID).orElseThrow(() -> new AccountException("Account doesn't exist."));
        double total = 0;
        if(amount > account.getBalance()){
            throw new RuntimeException("Insufficient Balance.");
        } else{
             total = account.getBalance() - amount;
        }
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transactions transaction = new Transactions();
        transaction.setAccountId(accountID);
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> allAccounts = accountRepository.findAll();
        return allAccounts.stream().map(AccountMapper::mapToAccountDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO deleteAccount(Long accountID) {
        Account acc = new Account();
        try {
            acc = accountRepository.findById(accountID).orElseThrow();
            accountRepository.deleteById(accountID);
            System.out.println("Account deleted successfully    ");
        }catch (Exception e){
            throw new AccountException("Account ille");
        }
        return AccountMapper.mapToAccountDTO(acc);
    }

    @Override
    public void transferFund(TransferFundDTO transferFundDTO) {
//        Retrieve sender account
        Account senderAccount = accountRepository.findById(transferFundDTO.fromAccountID()).orElseThrow(() -> new AccountException("Sender Account Not Found"));
//        Retrieve receiver account
        Account receiverAccount = accountRepository.findById(transferFundDTO.toAccountID()).orElseThrow(() -> new AccountException("Receiver Account Not Found"));

//        Debit amount from sender account
        if(senderAccount.getBalance() < transferFundDTO.amount()){
            throw new AccountException("Insufficient_Funds");
        }else{
            senderAccount.setBalance(senderAccount.getBalance() - transferFundDTO.amount());
        }

//        Credit amount to receiver account
        receiverAccount.setBalance(receiverAccount.getBalance() + transferFundDTO.amount());

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transactions transactionSent = new Transactions();
        transactionSent.setAccountId(senderAccount.getAccountID());
        transactionSent.setAmount(transferFundDTO.amount());
        transactionSent.setTransactionType(TRANSACTION_TYPE_TRANSFER_SENT);
        transactionSent.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transactionSent);

        Transactions transactionReceived = new Transactions();
        transactionReceived.setAccountId(receiverAccount.getAccountID());
        transactionReceived.setAmount(transferFundDTO.amount());
        transactionReceived.setTransactionType(TRANSACTION_TYPE_TRANSFER_RECEIVED);
        transactionReceived.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transactionReceived);
    }

    @Override
    public List<TransactionDTO> getAllTransactionsById(Long id) {
        List<Transactions> transactionList = transactionRepository.findByAccountIdOrderByTimestampDesc(id);

        return transactionList.stream().map(AccountServiceImpl::convertEntityToDTO).collect(Collectors.toList());

    }

    private static TransactionDTO convertEntityToDTO(Transactions transaction){
        TransactionDTO transactionDTO = new TransactionDTO(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );
        return transactionDTO;
    }

}
