package com._projects.banking_application.controller;

import com._projects.banking_application.dto.AccountDTO;

import com._projects.banking_application.dto.TransactionDTO;

import com._projects.banking_application.dto.TransferFundDTO;
import com._projects.banking_application.service.AccountService;
import com._projects.banking_application.service.impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bankingapi/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
//    Add account REST API
    @PostMapping
    public ResponseEntity<AccountDTO> addAccountDTO(@RequestBody AccountDTO accountDTO){
        return new ResponseEntity<>(accountService.createAccount(accountDTO), HttpStatus.CREATED);
    }

//    get account rest api
    @GetMapping("{id}")
    public ResponseEntity<AccountDTO> getAccountbyID(@PathVariable("id") Long accountID){
        AccountDTO accountDTO = accountService.getAccountByID(accountID);
        return ResponseEntity.ok(accountDTO);
    }

//    deposit amount rest api
    @PutMapping("{id}/deposit")
    public  ResponseEntity<AccountDTO> depositAmount(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        double depositAmt = request.get("amount");
        AccountDTO accountDTO = accountService.depositAmount(id, depositAmt);
        return ResponseEntity.ok(accountDTO);
    }

//    withdraw REST api
    @PutMapping("{id}/withdraw")
    public ResponseEntity<AccountDTO> withdrawAmount(@PathVariable Long id, @RequestBody Map<String, Double> request){
        double withdrawAmt = request.get("amount");
        AccountDTO accountDTO = accountService.withdrawAmount(id, withdrawAmt);
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("all")
    public ResponseEntity<List<AccountDTO>> allAccounts(){
        List<AccountDTO> all = accountService.getAllAccounts();
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable Long id){
        AccountDTO accountDTO = accountService.deleteAccount(id);
        return ResponseEntity.ok(accountDTO);
    }

//    Build transfer fund rest api
    @PostMapping("transfer")
    public ResponseEntity<String> transferFund(@RequestBody TransferFundDTO transferFundDTO){
        accountService.transferFund(transferFundDTO);
        return ResponseEntity.ok("Transfer successful.");
    }

//    Rest API for getting all transactions by a specific id.
    @GetMapping("{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAllTransactionsById(id));
    }


}
