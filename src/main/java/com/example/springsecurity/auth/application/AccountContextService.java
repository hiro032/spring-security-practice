package com.example.springsecurity.auth.application;

import com.example.springsecurity.auth.domain.Account;
import com.example.springsecurity.auth.domain.AccountContext;
import com.example.springsecurity.auth.repository.JpaAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AccountContextService implements UserDetailsService {

    @Autowired
    private JpaAccountRepository repository;


    @Override
    public AccountContext loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("아이디에 해당하는 계정이 없습니다."));

        return AccountContext.fromAccount(account);
    }
}
