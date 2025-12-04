package com.test.todo.api.controller;


import com.test.todo.api.model.DefaultErrorResponseV1;
import com.test.todo.core.exception.AccessForbiddenException;
import com.test.todo.core.exception.DataConflictException;
import com.test.todo.core.exception.NotFoundException;
import com.test.todo.core.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    private final Clock clock;

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<DefaultErrorResponseV1> notFoundException(
            NotFoundException ex
    ) {
        log.error(ex.getMessage(), ex);
        final var response = new DefaultErrorResponseV1();
        response.setMessage(ex.getMessage());
        response.setStatusCode(404);
        response.setTimestamp(OffsetDateTime.now(clock));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    private ResponseEntity<DefaultErrorResponseV1> unauthorizedException(
            UnauthorizedException ex
    ) {
        log.error(ex.getMessage(), ex);
        final var response = new DefaultErrorResponseV1();
        response.setMessage(ex.getMessage());
        response.setStatusCode(401);
        response.setTimestamp(OffsetDateTime.now(clock));
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    private ResponseEntity<DefaultErrorResponseV1> accessForbiddenException(
            AccessForbiddenException ex
    ) {
        log.error(ex.getMessage(), ex);
        final var response = new DefaultErrorResponseV1();
        response.setMessage(ex.getMessage());
        response.setStatusCode(403);
        response.setTimestamp(OffsetDateTime.from(OffsetDateTime.now(clock)));
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(DataConflictException.class)
    private ResponseEntity<DefaultErrorResponseV1> dataConflictException(
            DataConflictException ex
    ) {
        log.error(ex.getMessage(), ex);
        final var response = new DefaultErrorResponseV1();
        response.setMessage(ex.getMessage());
        response.setStatusCode(409);
        response.setTimestamp(OffsetDateTime.from(OffsetDateTime.now(clock)));
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}
