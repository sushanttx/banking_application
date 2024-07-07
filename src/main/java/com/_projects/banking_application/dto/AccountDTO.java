package com._projects.banking_application.dto;

//import lombok.*;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class AccountDTO {
//    private Long accountID;
//    private String accountHolderName;
//    private double balance;
//}

    public record AccountDTO(Long id, String accountHolderName, double balance){

}
