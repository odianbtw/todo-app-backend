package com.test.todo.core.service;

import org.springframework.stereotype.Service;

@Service
public class StubEmailService implements EmailService {

    @Override
    public void sendMessage(String email, String message) {
    }
}
