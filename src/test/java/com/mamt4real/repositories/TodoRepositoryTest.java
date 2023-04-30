package com.mamt4real.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.mamt4real.models.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

class TodoRepositoryTest {

    private final TodoRepository repository = new TodoRepository();

    @BeforeEach
    void setUp() {
        repository.purge() ;
    }

    @Test
    void testCreateOne() {
        Todo todo = new Todo("Buy groceries", "Milk, bread, eggs");
        long id = repository.createOne(todo);
        assertTrue(id > 0);
        assertEquals(todo, repository.getOne(id).orElse(null));
    }

    @Test
    void testGetAll() {
        List<Todo> todos = repository.getAll();
        assertNotNull(todos);
    }

    @Test
    void testGetOne() {
        Todo todo = new Todo("Buy groceries", "Milk, bread, eggs");
        long id = repository.createOne(todo);
        assertEquals(todo, repository.getOne(id).orElse(null));
    }

    @Test
    void testUpdate() {
        Todo todo = new Todo("Buy groceries", "Milk, bread, eggs");
        long id = repository.createOne(todo);
        todo.setName("Buy vegetables");
        repository.update(todo);
        assertEquals(todo, repository.getOne(id).orElse(null));
    }

    @Test
    void testDelete() {
        Todo todo = new Todo("Buy groceries", "Milk, bread, eggs");
        long id = repository.createOne(todo);
        repository.delete(todo);
        assertFalse(repository.getOne(id).isPresent());
    }

    @Test
    void testGetActiveTodos() {
        Todo todo1 = new Todo("Buy groceries", "Milk, bread, eggs");
        repository.createOne(todo1);
        Todo todo2 = new Todo("Do laundry", "");
        repository.createOne(todo2);
        todo2.setCompleted(true);
        repository.update(todo2);
        List<Todo> activeTodos = repository.getActiveTodos();
        assertEquals(1, activeTodos.size());
        assertEquals(todo1, activeTodos.get(0));
    }

    @Test
    void testGetCompletedTodos() {
        Todo todo1 = new Todo("Buy groceries", "Milk, bread, eggs");
        repository.createOne(todo1);
        Todo todo2 = new Todo("Do laundry", "");
        repository.createOne(todo2);
        todo2.setCompleted(true);
        repository.update(todo2);
        List<Todo> completedTodos = repository.getCompletedTodos();
        assertEquals(1, completedTodos.size());
        assertEquals(todo2, completedTodos.get(0));
    }

    @Test
    void testGetUserTodos() {
        Todo todo1 = new Todo("Buy groceries", "Milk, bread, eggs");
        todo1.setUserId(1);
        repository.createOne(todo1);
        Todo todo2 = new Todo("Do laundry", "");
        todo2.setUserId(2);
        repository.createOne(todo2);
        List<Todo> userTodos = repository.getUserTodos(1);
        assertEquals(1, userTodos.size());
        assertEquals(todo1, userTodos.get(0));
    }

    @Test
    void testMarkTodoAsCompleted() {
        Todo todo = new Todo("Buy groceries", "Milk, bread, eggs");
        long id = repository.createOne(todo);
        repository.markTodoAsCompleted(id);
        assertTrue(Objects.requireNonNull(repository.getOne(id).orElse(null)).isCompleted());
    }


    // Additional tests for search methods can be added here
    @Test
    public void testSearchTodosByName() {
        // Add some todos
        Todo todo1 = new Todo("shopping", "Buy milk, eggs, and bread from the supermarket", 1);
        repository.createOne(todo1);

        Todo todo2 = new Todo("shopping", "Complete the project and submit it before the deadline", 2);
        repository.createOne(todo2);

        List<Todo> todos = repository.searchTodosByName("shopping");
        Assertions.assertEquals(2, todos.size());
        for (Todo todo : todos) {
            Assertions.assertTrue(todo.getName().toLowerCase().contains("shopping"));
        }
    }

    @Test
    public void testSearchTodosByDescription() {

        Todo todo3 = new Todo("Call friend", "Call John to catch up on old times and give gift", 1);
        repository.createOne(todo3);

        Todo todo4 = new Todo("Read book", "Finish reading 'The Alchemist' and gift it out", 2);
        todo4.setCompleted(true); // Mark this todo as completed
        repository.createOne(todo4);

        List<Todo> todos = repository.searchTodosByDescription("gift");
        Assertions.assertEquals(2, todos.size());
        for (Todo todo : todos) {
            Assertions.assertTrue(todo.getDescription().toLowerCase().contains("gift"));
        }
    }

    @Test
    public void testSearchTodosByDate() {
        Todo todo5 = new Todo("Pay bills", "Pay electricity and water bills by the end of the month", 1);
        repository.createOne(todo5);

        Todo todo6 = new Todo("Go for a run", "Run for at least 30 minutes in the park", 2);
        repository.createOne(todo6);
        String date = "2023-05-10";
        todo6.setDateCreated(date);

        todo5.setDateCreated(date);
        List<Todo> todos = repository.searchTodosByDate("2023-05-10");
        Assertions.assertEquals(2, todos.size());
        for (Todo todo : todos) {
            Assertions.assertEquals(date, todo.getDateCreated());
        }
    }
}
