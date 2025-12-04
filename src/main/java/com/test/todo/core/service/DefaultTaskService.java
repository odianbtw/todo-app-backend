package com.test.todo.core.service;

import com.test.todo.core.domain.model.Task;
import com.test.todo.core.exception.NotFoundException;
import com.test.todo.core.model.PagedResponse;
import com.test.todo.core.model.Query;
import com.test.todo.core.port.in.TaskService;
import com.test.todo.core.port.out.TaskDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class DefaultTaskService implements TaskService {

    private final TaskDao taskDao;
    private final IdGenerator idGenerator;

    @Override
    public PagedResponse<Task> findAll(UUID userId, Query query) {
        return taskDao.findAll(userId, query);
    }

    @Override
    public Task create(UUID userId, Task task) {
        task.setId(idGenerator.generateUuid());
        task.setCompleted(false);
        task.setUserId(userId);
        return taskDao.create(task);
    }

    @Override
    public Task findById(UUID userId, UUID taskId) {
        final var task = taskDao.findById(taskId);
        if (task.isPresent() && Objects.equals(userId, task.get().getUserId())) {
            return task.get();
        } else {
            throw new NotFoundException("You don't have any tasks with provided id.");
        }
    }

    @Override
    public Task update(UUID userId, Task task) {
        return taskDao.update(userId, task);
    }
}
