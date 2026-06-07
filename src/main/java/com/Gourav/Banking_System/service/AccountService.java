package com.Gourav.Banking_System.service;

import com.Gourav.Banking_System.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountByAccountNumber(String accountNumber);

    AccountDto deposite(String accountNumber, Double amount);

    AccountDto withdrawl(String accountNumber, Double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccountByAccountNumber(String accountNumber);

    AccountDto transfer(String fromAccountNumber,
                        String toAccountNumber,
                        Double amount);

    AccountDto creditInterest(String accountNumber);

    AccountDto matureFd(String accountNumber);
}