package com.test.todo.core.port.in;

import com.test.todo.core.domain.model.Task;
import com.test.todo.core.model.PagedResponse;
import com.test.todo.core.model.Query;

import java.util.UUID;

public interface TaskService {
    PagedResponse<Task> findAll(UUID userId, Query query);
    Task create(UUID userId, Task task);
    Task findById(UUID userId, UUID taskId);
    Task update(UUID userId, Task task);
}
