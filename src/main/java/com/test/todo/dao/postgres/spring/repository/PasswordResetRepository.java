package com.test.todo.dao.postgres.spring.repository;

import com.test.todo.dao.postgres.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;


public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {
    @Query("from PasswordReset p where p.token = :token and p.expiresAt > :now and p.used = false")
    Optional<PasswordReset> findValidToken(
            @Param("token") String token,
            @Param("now") Instant now
    );
}
