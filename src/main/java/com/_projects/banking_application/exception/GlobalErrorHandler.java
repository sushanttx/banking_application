package com._projects.banking_application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalErrorHandler {

//    Handle specific exception - Account Exception
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDetails> handlerAccountException(
            AccountException accountException,
            WebRequest webRequest
    ){
        String errorCode = "Account_Not_Found_LOL.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(accountException.getMessage().contains("Insufficient_Funds")){
            errorCode = "Insufficient_Balance_In_Sender_Account.";
            status = HttpStatus.BAD_REQUEST;
        }
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                accountException.getMessage(),
                webRequest.getDescription(false),
                errorCode
        );
                return new ResponseEntity<>(errorDetails, status);
    }


//    Handle Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(
            Exception exception,
            WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "Internal_Server_Error."
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
