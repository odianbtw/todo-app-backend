package com.test.todo.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private UUID id;
    private UUID userId;
    private String name;
    private String description;
    private boolean completed;
    private Instant completedAt;
}
