package com.test.todo.dao.postgres.mapper;


import com.test.todo.core.domain.model.Task;
import com.test.todo.dao.postgres.model.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskEntityMapper {
    TaskEntity toTaskEntity(Task task);
    Task toTask(TaskEntity entity);
}
