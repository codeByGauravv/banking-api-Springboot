package com.Gourav.Banking_System.dto;

import com.Gourav.Banking_System.enums.AccountType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

//@Data
//@AllArgsConstructor
//public class AccountDto {
//    private Long id;
//    private String accountHolderName;
//    private double balance;
//}
public record AccountDto(
        Long id,
        String accountNumber,
        String accountHolderName,
        double balance,
        AccountType accountType) {
}