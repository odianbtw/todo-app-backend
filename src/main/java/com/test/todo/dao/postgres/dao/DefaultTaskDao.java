package com.test.todo.dao.postgres.dao;

import com.test.todo.core.domain.model.Task;
import com.test.todo.core.exception.NotFoundException;
import com.test.todo.core.model.PageInfo;
import com.test.todo.core.model.PagedResponse;
import com.test.todo.core.model.Query;
import com.test.todo.core.port.out.TaskDao;
import com.test.todo.dao.postgres.mapper.TaskEntityMapper;
import com.test.todo.dao.postgres.model.TaskEntity;
import com.test.todo.dao.postgres.spring.repository.TaskRepository;
import com.test.todo.dao.postgres.util.SpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class DefaultTaskDao implements TaskDao {

    private final TaskEntityMapper taskMapper;
    private final TaskRepository taskRepository;
    private final Clock clock;
    private final SpecificationFactory<TaskEntity> specificationFactory;

    @Override
    public PagedResponse<Task> findAll(UUID userId, Query query) {
        final var pageable = PageRequest.of(
                query.getPage().pageNumber(),
                query.getPage().pageSize(),
                Sort.Direction.fromString(query.getSort().order().toString()),
                query.getSort().sortBy()
        );
        final var spec = specificationFactory.createSpecification(query, userId);
        final var page = taskRepository.findAll(spec, pageable);
        return new PagedResponse<>(
                page.get().map(taskMapper::toTask).toList(),
                new PageInfo(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalElements(),
                        page.getTotalPages()
                )
        );
    }

    @Override
    public Task create(Task task) {
        final var entity = taskMapper.toTaskEntity(task);
        entity.setCreatedAt(clock.instant());
        entity.setUpdatedAt(clock.instant());
        return taskMapper.toTask(
                taskRepository.saveAndFlush(entity)
        );
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return taskRepository
                .findById(id)
                .map(taskMapper::toTask);
    }

    @Override
    public Task update(UUID userId, Task task) {
        final var optional = taskRepository.findById(task.getId());
        if (optional.isEmpty() || !Objects.equals(userId, optional.get().getUserId()))
            throw new NotFoundException("You don't have any tasks with provided id.");
        final var entity = optional.get();
        entity.setName(task.getName());
        entity.setDescription(task.getDescription());
        handleCompletion(entity, task.isCompleted());
        entity.setUpdatedAt(clock.instant());
        taskRepository.flush();
        return taskMapper.toTask(
                entity
        );
    }

    private void handleCompletion(TaskEntity entity, boolean completed) {
        if (!entity.isCompleted() && completed) {
            entity.setCompleted(true);
            entity.setCompletedAt(clock.instant());
        } else if (entity.isCompleted() && !completed) {
            entity.setCompleted(false);
            entity.setCompletedAt(null);
        }
    }
}
