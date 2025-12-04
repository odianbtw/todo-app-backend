package com.test.todo.dao.postgres.util;

import com.test.todo.core.model.Query;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface SpecificationFactory<T>{
    Specification<T> createSpecification(Query query, UUID userId);
}
