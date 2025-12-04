package com.test.todo.dao.postgres.spring.repository;

import com.test.todo.dao.postgres.model.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID>,
        JpaSpecificationExecutor<TaskEntity> {


}
