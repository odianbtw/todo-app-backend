package com.test.todo.core.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultIdGenerator implements IdGenerator {

    @Override
    public UUID generateUuid() {
        return UUID.randomUUID();
    }
}
