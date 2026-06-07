package com.Gourav.Banking_System.controller;

import com.Gourav.Banking_System.dto.AccountDto;
import com.Gourav.Banking_System.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(
            @RequestBody AccountDto accountDto) {

        return new ResponseEntity<>(
                accountService.createAccount(accountDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccount(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                accountService.getAccountByAccountNumber(accountNumber));
    }

    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<AccountDto> deposit(
            @PathVariable String accountNumber,
            @RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");

        return ResponseEntity.ok(
                accountService.deposite(accountNumber, amount));
    }

    @PutMapping("/{accountNumber}/withdrawl")
    public ResponseEntity<AccountDto> withdrawl(
            @PathVariable String accountNumber,
            @RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");

        return ResponseEntity.ok(
                accountService.withdrawl(accountNumber, amount));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

        return ResponseEntity.ok(
                accountService.getAllAccounts());
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable String accountNumber) {

        accountService.deleteAccountByAccountNumber(accountNumber);

        return ResponseEntity.ok(
                "Account deleted successfully");
    }

    @PutMapping("/transfer")
    public ResponseEntity<AccountDto> transfer(
            @RequestBody Map<String, Object> request) {

        String fromAccountNumber =
                request.get("fromAccountNumber").toString();

        String toAccountNumber =
                request.get("toAccountNumber").toString();

        Double amount =
                Double.valueOf(
                        request.get("amount").toString());

        return ResponseEntity.ok(
                accountService.transfer(
                        fromAccountNumber,
                        toAccountNumber,
                        amount));
    }

    @PutMapping("/{accountNumber}/interest")
    public ResponseEntity<AccountDto> interest(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                accountService.creditInterest(accountNumber));
    }

    @PutMapping("/{accountNumber}/mature")
    public ResponseEntity<AccountDto> matureFd(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                accountService.matureFd(accountNumber));
    }
}