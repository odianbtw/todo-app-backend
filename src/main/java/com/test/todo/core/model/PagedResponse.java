package com.test.todo.core.model;

import java.util.List;

public record PagedResponse<T>(
        List<T> data,
        PageInfo page
){
}
