package com.test.todo.dao.postgres.util;

import com.test.todo.core.model.Query;
import com.test.todo.dao.postgres.model.TaskEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class TaskSpecificationFactory implements SpecificationFactory<TaskEntity>{

    @Override
    public Specification<TaskEntity> createSpecification(Query query, UUID userId) {
        final var completed = query.getParams().get("completed");
        final Specification<TaskEntity> user =
                (root, q, cb) -> cb.equal(root.get("userId"), userId);
        final Specification<TaskEntity> spec =
                (root, q, cb) -> cb.equal(root.get("completed"), Boolean.parseBoolean(completed));
        return user.and(spec);
    }
}
