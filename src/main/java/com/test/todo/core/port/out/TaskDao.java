package com.test.todo.core.port.out;

import com.test.todo.core.domain.model.Task;
import com.test.todo.core.model.PagedResponse;
import com.test.todo.core.model.Query;

import java.util.Optional;
import java.util.UUID;

public interface TaskDao {
    PagedResponse<Task> findAll(UUID userId, Query query);
    Task create(Task task);
    Optional<Task> findById(UUID id);
    Task update(UUID userId, Task task);
}
