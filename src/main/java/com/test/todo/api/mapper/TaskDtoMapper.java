package com.test.todo.api.mapper;


import com.test.todo.api.model.*;
import com.test.todo.core.domain.model.Task;
import com.test.todo.core.model.PagedResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskDtoMapper {
    Task toTask(CreateTaskRequestV1 createTaskRequestV1);
    TaskRepresentationV1 toTaskRepresentationV1(Task task);
    Task toTaskUpdate(UpdateTaskV1 updateTaskV1);
    TaskItemRepresentationV1 toTaskItemRepresentationV1(Task task);
    @Mapping(target = "page", source = "page")
    @Mapping(target = "data", source = "data")
    GetAllTasksResponseV1 toGetAllTasksResponseV1(PagedResponse<Task> tasks);
}
