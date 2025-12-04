package com.test.todo.dao.postgres.dao;

import com.test.todo.core.domain.model.UserAccount;
import com.test.todo.core.exception.NotFoundException;
import com.test.todo.core.exception.UnauthorizedException;
import com.test.todo.core.port.out.UserIdentityDao;
import com.test.todo.dao.postgres.model.PasswordReset;
import com.test.todo.dao.postgres.spring.repository.PasswordResetRepository;
import com.test.todo.dao.postgres.spring.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class DefaultUserIdentityDao implements UserIdentityDao {

    private final PasswordResetRepository passwordResetRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;

    @Override
    public void createResetPasswordToken(UserAccount userAccount, String token) {
        final var expirationDate = clock.instant().plus(15, ChronoUnit.MINUTES);
        final var resetToken = PasswordReset.builder()
                .userAccountId(userAccount.getId())
                .token(token)
                .expiresAt(expirationDate)
                .createdAt(clock.instant())
                .build();
        passwordResetRepository.save(resetToken);
    }

    @Override
    public void changeUserPasswordViaToken(String token, String newPassword) {
        final var passwordToken = passwordResetRepository.findValidToken(token, clock.instant())
                .orElseThrow(() -> new NotFoundException("Couldn't find active password recovery token with provided data."));
        final var user = userAccountRepository.findById(passwordToken.getUserAccountId())
                .orElseThrow(() -> new NotFoundException("Couldn't find user account associated with provided token."));
        passwordToken.setUsed(true);
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public void changeUserPassword(UUID userId, String currentPassword, String newPassword) {
        final var user = userAccountRepository.findById(userId);
        if(user.isPresent() && passwordEncoder.matches(currentPassword, user.get().getPassword())) {
            user.get().setPassword(passwordEncoder.encode(newPassword));
        } else {
            throw new UnauthorizedException("Couldn't change password with provided data.");
        }
    }

}
