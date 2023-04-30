package com.mamt4real.models;

import com.mamt4real.interfaces.HasID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Todo implements HasID {

    private long todoId;
    private String name;
    private String description;
    private long userId;
    private boolean completed;
    private String dateCreated;

    public Todo() {
    }

    public Todo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Todo(String name, String description, long userId) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.completed = false;
        this.dateCreated = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return todoId == todo.todoId && userId == todo.userId && name.equals(todo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId, name, userId);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "todoId=" + todoId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", completed=" + completed +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }

    public String toRow(){
        String truncatedDesc = description.length() >= 20 ? description.substring(0,17) + "..." : description;
        return String.format("|%-3d|%-20s|%-20s|%-10s|%15s|", todoId, name, truncatedDesc,completed, dateCreated);
    }

    @Override
    public long getId() {
        return getTodoId();
    }

    @Override
    public void setId(long newId) {
        setTodoId(newId);
    }
}
