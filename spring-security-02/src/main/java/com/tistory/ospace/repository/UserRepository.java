package com.tistory.ospace.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public void add(User user) {
        users.put(user.getId(), user);
    }

    public User get(String id) {
        return users.get(id);
    }

    public void remove(String id) { users.remove(id); }
}
