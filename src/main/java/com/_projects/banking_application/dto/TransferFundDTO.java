package com._projects.banking_application.dto;

public record TransferFundDTO (Long fromAccountID,
                              Long toAccountID,
                              double amount){

}
