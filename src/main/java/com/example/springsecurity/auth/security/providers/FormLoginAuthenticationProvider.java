package com.example.springsecurity.auth.security.providers;

import com.example.springsecurity.auth.domain.Account;
import com.example.springsecurity.auth.domain.AccountContext;
import com.example.springsecurity.auth.domain.AccountRepository;
import com.example.springsecurity.auth.security.token.PostAuthorizationToken;
import com.example.springsecurity.auth.security.token.PreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public FormLoginAuthenticationProvider(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String userPassword = token.getUserPassword();

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("아이디에 해당하는 유저를 찾을 수 없습니다."));

        checkPassword(account, userPassword);

        AccountContext accountContext = AccountContext.fromAccount(account);

        return PostAuthorizationToken.fromAccountContext(accountContext);
    }

    private void checkPassword(Account account, String userPassword) {
        if (!passwordEncoder.matches(userPassword, account.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
