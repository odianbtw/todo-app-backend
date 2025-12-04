package com.test.todo.core.service;

import com.test.todo.core.model.AuthToken;
import com.test.todo.core.port.in.AuthService;
import com.test.todo.core.port.in.UserService;
import com.test.todo.core.port.out.UserIdentityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class DefaultAuthService implements AuthService {

    private final UserService userService;
    private final AuthTokenService authTokenService;
    private final UserTokenService userTokenService;
    private final UserIdentityDao userIdentityDao;
    private final EmailService emailService;

    @Override
    public void createResetPasswordToken(String email) {
        final var user = userService.findByIdentifier(email);
        final var recoveryToken = userTokenService.generateResetPasswordToken();
        userIdentityDao.createResetPasswordToken(user, recoveryToken);
        emailService.sendMessage(user.getEmail(), recoveryToken);
    }

    @Override
    public void recoverPassword(String recoveryToken, String newPassword) {
        userIdentityDao.changeUserPasswordViaToken(recoveryToken, newPassword);
    }

    @Override
    public AuthToken login(String identifier, String password) {
        final var userAccount = userService.findByCredentials(identifier, password);
        return authTokenService.generateByUserAccount(userAccount);
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        return authTokenService.refreshToken(refreshToken);
    }
}
