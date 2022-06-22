package com.example.springsecurity.auth.security;

import com.example.springsecurity.auth.domain.Account;
import com.example.springsecurity.auth.domain.AccountContext;
import com.example.springsecurity.auth.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtFactoryTest {

    @Test
    void create_jwt_token() {
        Account account = new Account("hiro", "pwd123", UserRole.USER);
        AccountContext accountContext = AccountContext.fromAccount(account);

        JwtFactory factory = new JwtFactory();

        String token = factory.generateToken(accountContext);

        System.out.println("token = " + token);
    }
}