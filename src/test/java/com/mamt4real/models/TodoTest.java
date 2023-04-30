package com.mamt4real.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoTest {

    @Test
    public void testConstructor() {
        String name = "Do laundry";
        String description = "Wash clothes and fold them";
        long userId = 1;

        Todo todo = new Todo(name, description, userId);

        Assertions.assertEquals(name, todo.getName());
        Assertions.assertEquals(description, todo.getDescription());
        Assertions.assertEquals(userId, todo.getUserId());
        Assertions.assertFalse(todo.isCompleted());
        Assertions.assertEquals(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE), todo.getDateCreated());
    }

    @Test
    public void testEqualsAndHashCode() {
        Todo todo1 = new Todo("Do laundry", "Wash clothes and fold them", 1);
        todo1.setTodoId(1);

        Todo todo2 = new Todo("Do laundry", "Wash clothes and fold them", 1);
        todo2.setTodoId(1);

        Todo todo3 = new Todo("Buy groceries", "Milk, eggs, bread", 2);
        todo3.setTodoId(2);

        Assertions.assertEquals(todo1, todo2);
        Assertions.assertNotEquals(todo1, todo3);

        Assertions.assertEquals(todo1.hashCode(), todo2.hashCode());
        Assertions.assertNotEquals(todo1.hashCode(), todo3.hashCode());
    }

    @Test
    public void testToString() {
        Todo todo = new Todo("Do laundry", "Wash clothes and fold them", 1);
        todo.setTodoId(1);
        todo.setCompleted(false);
        todo.setDateCreated("2023-04-30");

        String expected = "Todo{todoId=1, name='Do laundry', description='Wash clothes and fold them', " +
                "userId=1, completed=false, dateCreated='2023-04-30'}";

        Assertions.assertEquals(expected, todo.toString());
    }

    @Test
    public void testToRow() {
        Todo todo = new Todo("Do laundry", "Wash clothes and fold them", 1);
        todo.setTodoId(1);
        todo.setCompleted(false);
        todo.setDateCreated("2023-04-30");

        String expected = String.format(
                "|%-3d|%-20s|%-20s|%-10s|%15s|", 1, todo.getName(), "Wash clothes and ...",
                todo.isCompleted(), todo.getDateCreated());

        Assertions.assertEquals(expected, todo.toRow());
    }
}
