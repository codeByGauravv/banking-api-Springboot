package com.Gourav.Banking_System.entity;

import com.Gourav.Banking_System.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    private double balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    // Savings
    private Double withdrawalLimit;
    private Double minimumBalance;

    // Current
    private Double overdraftLimit;

    // Savings & FD
    private Double interestRate;

    // FD
    private Double maturityAmount;
    private boolean matured;
}