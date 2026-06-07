package com.Gourav.Banking_System.Implimentations;

import com.Gourav.Banking_System.accountNumberGenerator.GenerateAccountNum;
import com.Gourav.Banking_System.dto.AccountDto;
import com.Gourav.Banking_System.entity.Account;
import com.Gourav.Banking_System.enums.AccountType;
import com.Gourav.Banking_System.mapper.AccountsMapper;
import com.Gourav.Banking_System.repositories.AccountRepositories;
import com.Gourav.Banking_System.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.Gourav.Banking_System.exceptions.InvalidTransactionException;
import com.Gourav.Banking_System.exceptions.ResponseNotFound;
import com.Gourav.Banking_System.exceptions.*;

@Service
public class AccountServiceIMPL implements AccountService {

    private final AccountRepositories accountRepositories;
    private final GenerateAccountNum generateAccountNum;

    public AccountServiceIMPL(AccountRepositories accountRepositories,
                              GenerateAccountNum generateAccountNum) {
        this.accountRepositories = accountRepositories;
        this.generateAccountNum = generateAccountNum;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        if (accountDto == null) {
            throw new InvalidTransactionException(
                    "Account details cannot be null");
        }

        if (accountDto.accountHolderName() == null ||
                accountDto.accountHolderName().trim().isEmpty()) {

            throw new InvalidTransactionException(
                    "Account holder name is required");
        }

        if (accountDto.balance() < 0) {
            throw new InvalidTransactionException(
                    "Initial balance cannot be negative");
        }

        if (accountDto.accountType() == null) {
            throw new InvalidTransactionException(
                    "Account type is required");
        }

        Account account = AccountsMapper.mapToAccount(accountDto);

        String accountNumber;

        do {
            accountNumber = generateAccountNum.generateAccountNumber();
        } while (accountRepositories.existsByAccountNumber(accountNumber));

        account.setAccountNumber(accountNumber);

        if (account.getAccountType() == AccountType.SAVINGS) {

            account.setWithdrawalLimit(50000.0);
            account.setMinimumBalance(1000.0);
            account.setOverdraftLimit(0.0);
            account.setInterestRate(4.0);

        } else if (account.getAccountType() == AccountType.CURRENT) {

            account.setWithdrawalLimit(200000.0);
            account.setMinimumBalance(0.0);
            account.setOverdraftLimit(50000.0);
            account.setInterestRate(0.0);

        } else if (account.getAccountType() == AccountType.FD) {

            account.setWithdrawalLimit(0.0);
            account.setMinimumBalance(0.0);
            account.setOverdraftLimit(0.0);
            account.setInterestRate(7.0);

            double maturityAmount =
                    account.getBalance()
                            + (account.getBalance() * 7 / 100);

            account.setMaturityAmount(maturityAmount);
            account.setMatured(false);

        } else {

            throw new InvalidTransactionException(
                    "Invalid Account Type");
        }

        Account savedAccount =
                accountRepositories.save(account);

        return AccountsMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountByAccountNumber(String accountNumber) {

        Account account = accountRepositories
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Account Not Found With Account Number : "
                                        + accountNumber));

        return AccountsMapper.mapToAccountDto(account);
    }
    @Override

    public AccountDto deposite(String accountNumber, Double amount) {

        Account account = accountRepositories
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Account Not Found With Account Number : "
                                        + accountNumber));

        if (amount <= 0) {
            throw new InvalidTransactionException(
                    "Amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);

        Account savedAccount =
                accountRepositories.save(account);

        return AccountsMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdrawl(String accountNumber, Double amount) {

        Account account = accountRepositories
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Account Not Found With Account Number : "
                                        + accountNumber));

        if (amount <= 0) {
            throw new InvalidTransactionException(
                    "Amount must be greater than zero");
        }

        if (account.getAccountType() == AccountType.SAVINGS) {

            if (amount > account.getWithdrawalLimit()) {
                throw new WithdrawalLimitExceededException(
                        "Savings withdrawal limit exceeded");
            }

            if (account.getBalance() - amount <
                    account.getMinimumBalance()) {

                throw new InsufficientBalanceException(
                        "Minimum balance of ₹1000 required");
            }

            if (account.getBalance() < amount) {
                throw new InsufficientBalanceException(
                        "Insufficient Balance");
            }
        }

        if (account.getAccountType() == AccountType.CURRENT) {

            double availableBalance =
                    account.getBalance()
                            + account.getOverdraftLimit();

            if (amount > account.getWithdrawalLimit()) {
                throw new WithdrawalLimitExceededException(
                        "Current withdrawal limit exceeded");
            }

            if (amount > availableBalance) {
                throw new OverdraftLimitExceededException(
                        "Overdraft limit exceeded");
            }
        }

        if (account.getAccountType() == AccountType.FD) {

            if (!account.isMatured()) {
                throw new FdNotMaturedException(
                        "FD not matured yet");
            }

            if (account.getBalance() < amount) {
                throw new InsufficientBalanceException(
                        "Insufficient Balance");
            }
        }

        account.setBalance(account.getBalance() - amount);

        Account savedAccount =
                accountRepositories.save(account);

        return AccountsMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {

        List<Account> accounts =
                accountRepositories.findAll();

        if (accounts.isEmpty()) {
            throw new ResponseNotFound(
                    "No Accounts Found");
        }

        return accounts.stream()
                .map(AccountsMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteAccountByAccountNumber(String accountNumber) {

        Account account = accountRepositories
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Account Not Found With Account Number : "
                                        + accountNumber));

        accountRepositories.delete(account);
    }

    @Override
    public AccountDto transfer(String fromAccountNumber,
                               String toAccountNumber,
                               Double amount) {

        Account sender = accountRepositories
                .findByAccountNumber(fromAccountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Sender Account Not Found : "
                                        + fromAccountNumber));

        Account receiver = accountRepositories
                .findByAccountNumber(toAccountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Receiver Account Not Found : "
                                        + toAccountNumber));

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new SelfTransferException(
                    "Cannot transfer money to the same account");
        }

        if (amount <= 0) {
            throw new InvalidTransactionException(
                    "Transfer amount must be greater than zero");
        }

        if (sender.getBalance() < amount) {
            throw new InsufficientBalanceException(
                    "Insufficient Balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepositories.save(sender);
        accountRepositories.save(receiver);

        return AccountsMapper.mapToAccountDto(sender);
    }

    @Override
    public AccountDto creditInterest(String accountNumber) {

        Account account = accountRepositories
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Account Not Found With Account Number : "
                                        + accountNumber));

        if (account.getAccountType() != AccountType.SAVINGS) {
            throw new InvalidTransactionException(
                    "Interest can only be credited to Savings Account");
        }

        double interest =
                account.getBalance()
                        * account.getInterestRate()
                        / 100 / 12;

        account.setBalance(
                account.getBalance() + interest);

        Account savedAccount =
                accountRepositories.save(account);

        return AccountsMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto matureFd(String accountNumber) {

        Account account = accountRepositories
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new ResponseNotFound(
                                "Account Not Found With Account Number : "
                                        + accountNumber));

        if (account.getAccountType() != AccountType.FD) {
            throw new InvalidTransactionException(
                    "This is not an FD Account");
        }

        if (account.isMatured()) {
            throw new InvalidTransactionException(
                    "FD is already matured");
        }

        account.setBalance(account.getMaturityAmount());
        account.setMatured(true);

        Account savedAccount =
                accountRepositories.save(account);

        return AccountsMapper.mapToAccountDto(savedAccount);
    }
}