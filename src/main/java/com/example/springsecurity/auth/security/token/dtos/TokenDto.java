package com.example.springsecurity.auth.security.token.dtos;

import lombok.Data;

@Data
public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
