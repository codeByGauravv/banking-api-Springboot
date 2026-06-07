package com.Gourav.Banking_System.accountNumberGenerator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateAccountNum {

    public String generateAccountNumber() {

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        sb.append(random.nextInt(9) + 1);

        for(int i=0;i<10;i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}