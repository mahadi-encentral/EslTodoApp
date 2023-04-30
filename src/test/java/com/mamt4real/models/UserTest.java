package com.mamt4real.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User("John Doe", "jdoe", "password", "admin");
        user.setId(1);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("jdoe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("admin", user.getRole());
        assertNotNull(user.getCreatedAt());

        user.setName("Jane Doe");
        user.setUsername("jane_doe");
        user.setPassword("new_password");
        user.setRole("client");

        assertEquals("Jane Doe", user.getName());
        assertEquals("jane_doe", user.getUsername());
        assertEquals("new_password", user.getPassword());
        assertEquals("client", user.getRole());
    }

    @Test
    void testToString() {
        User user = new User("John Doe", "jdoe", "password", "admin");
        user.setId(1);

        String expected = "User{userId=1, name='John Doe', username='jdoe', role='admin', createdAt=";
        assertTrue(user.toString().startsWith(expected));
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User("John Doe", "jdoe", "password", "admin");
        user1.setId(1);

        User user2 = new User("Jane Doe", "jane_doe", "new_password", "client");
        user2.setId(1);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        user2.setId(2);
        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
