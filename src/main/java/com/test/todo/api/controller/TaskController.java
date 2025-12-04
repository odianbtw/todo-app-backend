package com.test.todo.api.controller;

import com.test.todo.api.mapper.TaskDtoMapper;
import com.test.todo.api.model.*;
import com.test.todo.api.service.AuthUtils;
import com.test.todo.core.exception.BadRequestException;
import com.test.todo.core.model.Order;
import com.test.todo.core.model.Page;
import com.test.todo.core.model.Sort;
import com.test.todo.core.model.TaskQuery;
import com.test.todo.core.port.in.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService taskService;
    private final TaskDtoMapper taskMapper;

    @Override
    public ResponseEntity<TaskRepresentationV1> createTask(CreateTaskRequestV1 createTaskRequestV1) {
        log.info("Started creating task.");
        final var userId = AuthUtils.getAuthenticatedUserId();
        final var task = taskService.create(
                userId,
                taskMapper.toTask(createTaskRequestV1)
        );
        log.info("Finished creating task.");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                taskMapper.toTaskRepresentationV1(task)
        );
    }

    @Override
    public ResponseEntity<TaskRepresentationV1> findTaskById(UUID id) {
        log.info("Started finding task by id.");
        final var userId = AuthUtils.getAuthenticatedUserId();
        final var task = taskService.findById(userId, id);
        log.info("Finished finding task by id.");
        return ResponseEntity.ok(
                taskMapper.toTaskRepresentationV1(task)
        );
    }

    @Override
    public ResponseEntity<TaskRepresentationV1> updateTask(UUID id, UpdateTaskV1 updateTaskV1) {
        log.info("Started updating task by id.");
        final var userId = AuthUtils.getAuthenticatedUserId();
        final var task = taskMapper.toTaskUpdate(updateTaskV1);
        if (!id.equals(task.getId())) throw new BadRequestException("Id must be the same for path and request body");
        final var res = taskService.update(userId, task);
        log.info("Finished updating task by id.");
        return ResponseEntity.ok(
                taskMapper.toTaskRepresentationV1(res)
        );
    }

    @Override
    public ResponseEntity<GetAllTasksResponseV1> getTasks(
            Integer pageNumber,
            Integer pageSize,
            String order,
            String sort,
            Boolean completed
    ) {
        log.info("Started finding tasks.");
        final var userId = AuthUtils.getAuthenticatedUserId();
        final var query = new TaskQuery(
                new Page(pageSize,pageNumber),
                new Sort(Order.valueOf(order), sort),
                Map.of("completed", Boolean.toString(completed))
        );
        final var res = taskService.findAll(userId, query);
        log.info("Finished finding tasks.");
        return ResponseEntity.ok(taskMapper.toGetAllTasksResponseV1(res));
    }

}
