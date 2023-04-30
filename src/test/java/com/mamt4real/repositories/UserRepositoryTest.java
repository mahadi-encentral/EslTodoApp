package com.mamt4real.repositories;
import com.mamt4real.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository();

    @BeforeEach
    public void setUp() {
        userRepository.purge() ;
    }

    @Test
    public void testCreateOne() {
        User user = new User("John Doe", "johndoe", "password");
        long id = userRepository.createOne(user);
        Assertions.assertEquals(1, id);
    }

    @Test
    public void testGetAll() {
        User user1 = new User("John Doe", "johndoe", "password");
        User user2 = new User("Jane Doe", "janedoe", "password");
        userRepository.createOne(user1);
        userRepository.createOne(user2);
        Assertions.assertEquals(2, userRepository.getAll().size());
    }

    @Test
    public void testGetOne() {
        User user = new User("John Doe", "johndoe", "password");
        long id = userRepository.createOne(user);
        User retrievedUser = userRepository.getOne(id).orElse(null);
        Assertions.assertEquals(user, retrievedUser);
    }

    @Test
    public void testUpdate() {
        User user = new User("John Doe", "johndoe", "password");
        long id = userRepository.createOne(user);
        user.setUsername("newusername");
        userRepository.update(user);
        User updatedUser = userRepository.getOne(id).orElse(null);
        assert updatedUser != null;
        Assertions.assertEquals("newusername", updatedUser.getUsername());
    }

    @Test
    public void testDelete() {
        User user = new User("John Doe", "johndoe", "password");
        long id = userRepository.createOne(user);
        userRepository.delete(user);
        Assertions.assertNull(userRepository.getOne(id).orElse(null));
    }

    @Test
    public void testLogin() {
        User user = new User("John Doe", "johndoe", "password");
        userRepository.createOne(user);
        long result = userRepository.login("johndoe", "password");
        Assertions.assertEquals(1, result);
    }

    @Test
    public void testSignup() {
        long id = userRepository.signup("John Doe", "johndoe", "password");
        User createdUser = userRepository.getOne(id).orElse(null);
        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals("John Doe", createdUser.getName());
        Assertions.assertEquals("johndoe", createdUser.getUsername());
        Assertions.assertEquals("password", createdUser.getPassword());
    }
}
