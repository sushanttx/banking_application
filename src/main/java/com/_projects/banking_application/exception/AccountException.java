package com._projects.banking_application.exception;

import com._projects.banking_application.dto.AccountDTO;

public class AccountException extends RuntimeException{
    public AccountException(String message){
        super(message);
    }
}
