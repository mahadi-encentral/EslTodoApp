package com.mamt4real.models;

import com.mamt4real.interfaces.HasID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class User implements HasID {
    private long userId;

    private String name;

    private String username;

    private String password;

    private String role;

    private String createdAt;


    public User() {
    }

    public User(String name, String username, String password) {
        this(name, username, password, "client");
    }

    public User(String name, String username, String password, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public long getId() {
        return getUserId();
    }

    @Override
    public void setId(long newId) {
        setUserId(newId);
    }
}
