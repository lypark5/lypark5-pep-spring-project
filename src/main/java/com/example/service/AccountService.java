package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // CREATING ACCOUNT
    public Account addAccount(Account account) {
        // validate fields first
        validateAccount(account);

        // then create
        return accountRepository.save(account);
    }

    // LOGIN
    public Account login(String username, String password) {
        // finding account first
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);

        // check if account exists and password matches
        if (optionalAccount.isPresent() && optionalAccount.get().getPassword().equals(password)) {
            return optionalAccount.get();
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    // VALIDATE ACCOUNT
    private void validateAccount(Account account) {
        // check if username is null, but also if empty string.
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        // check if pw is at less than 4 min
        if (account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        // check if username exists using repo
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateAccountException("Username already exists");
        }
    }
}
