package com.example.springsecurity.auth.security.repository;

import com.example.springsecurity.auth.domain.Account;
import com.example.springsecurity.auth.domain.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {

    private Map<Long, Account> accounts = new HashMap<>();

    public Account save(final Long id, final Account account) {
        accounts.put(id, account);

        return account;
    }

    @Override
    public Optional<Account> findByUsername(final String username) {
        return accounts.values().stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst();
    }
}
