package com.mamt4real.repositories;
import com.google.common.collect.Lists;
import com.mamt4real.interfaces.SearchTodos;
import com.mamt4real.models.Todo;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class TodoRepository extends BaseRepository<Todo> implements SearchTodos {

    private final HashMap<Long, Todo> todos = storage;
    public TodoRepository() {
        super("todos.json", Todo.class);
    }

    private List<Todo> search(Predicate<Todo> predicate){
        List<Todo> values = Lists.newArrayList(todos.values());
        return values.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public List<Todo> searchTodosByName(String query) {
        return search(t -> t.getName().contains(query));
    }

    @Override
    public List<Todo> searchTodosByDescription(String query) {
        return search(t -> t.getDescription().contains(query));
    }

    @Override
    public List<Todo> searchTodosByDate(String query) {
        return search(t -> t.getDateCreated().contains(query));
    }

    public List<Todo> getActiveTodos(){
        return search(t -> !t.isCompleted());
    }

    @Override
    public List<Todo> getCompletedTodos() {
        return search(Todo::isCompleted);
    }

    public List<Todo> getUserTodos(long userId){
        return search(t -> t.getUserId() == userId);
    }

    public void markTodoAsCompleted(long todoId){
        if(todos.containsKey(todoId)){
            todos.get(todoId).setCompleted(true);
        }
    }

}
