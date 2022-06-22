package com.example.springsecurity.auth.security.providers;

import com.example.springsecurity.auth.domain.Account;
import com.example.springsecurity.auth.domain.UserRole;
import com.example.springsecurity.auth.security.repository.InMemoryAccountRepository;
import com.example.springsecurity.auth.security.token.PostAuthorizationToken;
import com.example.springsecurity.auth.security.token.PreAuthorizationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FormLoginAuthenticationProviderTest {

    private FormLoginAuthenticationProvider provider;
    private InMemoryAccountRepository repository = new InMemoryAccountRepository();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        this.provider = new FormLoginAuthenticationProvider(repository, passwordEncoder);
    }

    @DisplayName("토큰을 받아서 유효성을 체크후 성공한다면 인증 후 토큰 리턴")
    @Test
    void success() {
        final String username = "hiro";
        final String userPassword = "1234";

        saveUser(username, userPassword);

        PreAuthorizationToken preToken = new PreAuthorizationToken(username, userPassword);

        assertAll(
                () -> assertDoesNotThrow(() -> provider.authenticate(preToken)),
                () -> assertThat(provider.authenticate(preToken)).isInstanceOf(PostAuthorizationToken.class)
        );
    }

    @DisplayName("토큰 검증 실패")
    @Test
    void fail() {
        final String username = "hiro";
        final String userPassword = "1234";
        final String wrongPassword = "5678";

        saveUser(username, userPassword);

        PreAuthorizationToken preToken = new PreAuthorizationToken(username, wrongPassword);

        assertThatThrownBy(() -> provider.authenticate(preToken))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void saveUser(final String username, String userPassword) {
        Account account = new Account(username, passwordEncoder.encode(userPassword), UserRole.USER);

        repository.save(3L, account);
    }

    @DisplayName("CustomToken 에 대해서만 검증한다.")
    @Test
    void my_token() {
        Class<?> fakeToken = new FakeAuthorizationToken().getClass();

        assertThat(provider.supports(fakeToken)).isFalse();
    }

    @DisplayName("CustomToken 이 아니라면 검증하지 않는다.")
    @Test
    void not_my_token() {
        Class<?> fakeToken = new PreAuthorizationToken("hi", "test").getClass();

        assertThat(provider.supports(fakeToken)).isTrue();
    }

    class FakeAuthorizationToken {
    }
}