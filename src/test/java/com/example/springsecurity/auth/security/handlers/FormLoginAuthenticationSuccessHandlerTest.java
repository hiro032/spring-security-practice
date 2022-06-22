package com.example.springsecurity.auth.security.handlers;

import com.example.springsecurity.auth.domain.Account;
import com.example.springsecurity.auth.domain.AccountContext;
import com.example.springsecurity.auth.domain.UserRole;
import com.example.springsecurity.auth.security.token.PostAuthorizationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FormLoginAuthenticationSuccessHandlerTest {

    private FormLoginAuthenticationSuccessHandler handler;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        this.handler = new FormLoginAuthenticationSuccessHandler();
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
    }

    @DisplayName("인증 완료된 토큰을 통해 jwt token 을 생성해 응답한다.")
    @Test
    void response_jwt_token() throws ServletException, IOException {
        Account account = new Account("hiro", "1234", UserRole.USER);
        AccountContext accountContext = AccountContext.fromAccount(account);
        PostAuthorizationToken postToken = PostAuthorizationToken.fromAccountContext(accountContext);

        assertDoesNotThrow(() -> handler.onAuthenticationSuccess(request, response, postToken));
    }
}