package com.test.todo.core.model;

import java.util.Map;

public interface Query {
    Page getPage();
    Sort getSort();
    Map<String, String> getParams();
}
