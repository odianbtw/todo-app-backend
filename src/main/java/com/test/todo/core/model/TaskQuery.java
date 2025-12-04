package com.test.todo.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskQuery implements Query {

    private Page page;
    private Sort sort;
    private Map<String, String> params;

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
