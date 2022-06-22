package com.example.springsecurity.auth.domain;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByUsername(String username);
}
