package com.test.todo.api.controller;

import com.test.todo.api.mapper.TokenDtoMapper;
import com.test.todo.api.mapper.UserDtoMapper;
import com.test.todo.api.model.*;
import com.test.todo.core.port.in.AuthService;
import com.test.todo.core.port.in.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final UserService userService;
    private final AuthService authService;
    private final TokenDtoMapper tokenMapper;
    private final UserDtoMapper userMapper;

    @Override
    public ResponseEntity<Void> register(CreateUserAccountRequestV1 createUserAccountRequestV1) {
        log.info("Started registration of a user");
        userService.createUserAccount(userMapper.toUserAccount(createUserAccountRequestV1));
        log.info("Finished registration of a user");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ResponseEntity<LoginResponseV1> login(LoginRequestV1 loginRequestV1) {
        log.info("Started logging of a user");
        final var tokens = authService.login(
                loginRequestV1.getIdentifier(),
                loginRequestV1.getPassword()
        );
        log.info("Finished logging of a user");
        return ResponseEntity.ok(tokenMapper.toLoginResponseV1(tokens));
    }

    @Override
    public ResponseEntity<Void> crateResetPasswordToken(CreatePasswordResetTokenRequestV1 createPasswordResetTokenRequestV1) {
        log.info("Started creating password recovery token");
        authService.createResetPasswordToken(createPasswordResetTokenRequestV1.getEmail());
        log.info("Finished creating password recovery token");
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<Void> resetPassword(PasswordResetRequestV1 passwordResetRequestV1) {
        log.info("Started resetting user's password");
        authService.recoverPassword(passwordResetRequestV1.getToken(), passwordResetRequestV1.getNewPassword());
        log.info("Finished resetting user's password");
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    @Override
    public ResponseEntity<GenerateAccessTokenResponseV1> refresh(
            GenerateAccessTokenRequestV1 generateAccessTokenRequestV1
    ) {
        log.info("Started refreshing access token via refresh token.");
        final var tokens = authService.refreshToken(generateAccessTokenRequestV1.getRefreshToken());
        log.info("Finished refreshing access token via refresh token.");
        return ResponseEntity.ok(
                tokenMapper.toGenerateAccessTokenResponseV1(tokens)
        );
    }
}
