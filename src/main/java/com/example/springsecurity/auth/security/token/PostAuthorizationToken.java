package com.example.springsecurity.auth.security.token;

import com.example.springsecurity.auth.domain.AccountContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static PostAuthorizationToken fromAccountContext(final AccountContext accountContext) {
        return new PostAuthorizationToken(accountContext, accountContext.getPassword(), accountContext.getAuthorities());
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }

    public AccountContext getAccountContext() {
        AccountContext accountContext = (AccountContext) super.getPrincipal();
        return accountContext;
    }
}
