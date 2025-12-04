package com.test.todo.core.model;

public record PageInfo(
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
}
