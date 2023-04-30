package com.mamt4real.interfaces;

import com.mamt4real.models.Todo;

import java.util.List;

public interface SearchTodos {
    List<Todo> searchTodosByName(String query);
    List<Todo> searchTodosByDescription(String query);
    List<Todo> searchTodosByDate(String query);

    List<Todo> getActiveTodos();

    List<Todo> getCompletedTodos();
}
