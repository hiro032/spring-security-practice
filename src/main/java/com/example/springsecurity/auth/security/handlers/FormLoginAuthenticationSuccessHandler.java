package com.example.springsecurity.auth.security.handlers;

import com.example.springsecurity.auth.domain.AccountContext;
import com.example.springsecurity.auth.security.JwtFactory;
import com.example.springsecurity.auth.security.token.PostAuthorizationToken;
import com.example.springsecurity.auth.security.token.dtos.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter <-authentication token- provider
 * filter -jwt token-> handler
 * handler -jwt token to dto & response -> client
 */
@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtFactory jwtFactory = new JwtFactory();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PostAuthorizationToken token = (PostAuthorizationToken) authentication;

        AccountContext accountContext = token.getAccountContext();

        String tokenString = jwtFactory.generateToken(accountContext);

        TokenDto tokenDto = writeDto(tokenString);

        processResponse(response, tokenDto);
    }

    private TokenDto writeDto(final String token) {
        return new TokenDto(token);
    }

    private void processResponse(HttpServletResponse response, TokenDto tokenDto) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(tokenDto));
    }
}
