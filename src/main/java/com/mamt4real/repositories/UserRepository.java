package com.mamt4real.repositories;

import com.mamt4real.models.User;
import java.util.HashMap;
import java.util.Optional;

public class UserRepository extends BaseRepository<User>{
    private final HashMap<Long, User> users = storage;

    public UserRepository() {
        super("users.json", User.class);
    }

    public long login(String username, String password) {
        Optional<User> user = users.values().stream().filter(
                u -> u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)
        ).findFirst();
        return user.isEmpty() ? 0 : 1;
    }

    public long signup(String name, String username, String password) {
        return createOne(new User(name, username, password));
    }

}
