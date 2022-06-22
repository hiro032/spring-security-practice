package com.example.springsecurity.auth.repository;

import com.example.springsecurity.auth.domain.Account;
import com.example.springsecurity.auth.domain.AccountRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAccountRepository extends AccountRepository, JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
