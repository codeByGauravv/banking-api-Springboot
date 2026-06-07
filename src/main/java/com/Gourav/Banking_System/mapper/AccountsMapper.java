package com.Gourav.Banking_System.mapper;

import com.Gourav.Banking_System.dto.AccountDto;
import com.Gourav.Banking_System.entity.Account;

public class AccountsMapper {

    public static Account mapToAccount(AccountDto accountDto){

        Account account = new Account();

        account.setId(accountDto.id());
        account.setAccountNumber(accountDto.accountNumber());
        account.setAccountHolderName(accountDto.accountHolderName());
        account.setBalance(accountDto.balance());
        account.setAccountType(accountDto.accountType());

        return account;
    }

    public static AccountDto mapToAccountDto(Account account){

        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountHolderName(),
                account.getBalance(),
                account.getAccountType()
        );
    }
}