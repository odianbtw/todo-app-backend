package com.test.todo.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class DefaultUserTokenService implements UserTokenService {

    private final SecureRandom random;

    @Override
    public String generateResetPasswordToken() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}
