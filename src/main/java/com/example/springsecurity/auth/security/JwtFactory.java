package com.example.springsecurity.auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springsecurity.auth.domain.AccountContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtFactory {

    //FIXME
    private static String signingKey = "jwttest";

    public String generateToken(final AccountContext accountContext) {
        String token = null;

        try {
            token = JWT.create()
                    .withIssuer("hiro")
                    .withClaim("name", accountContext.getUsername())
                    .withClaim("user_role", accountContext.getAccount().getUserRole().getRoleName())
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(signingKey);
    }
}
