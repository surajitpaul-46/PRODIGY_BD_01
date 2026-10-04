package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<UUID, User> userMap = new ConcurrentHashMap<>();

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        userMap.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(userMap.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public boolean existsById(UUID id) {
        return userMap.containsKey(id);
    }

    public boolean deleteById(UUID id) {
        return userMap.remove(id) != null;
    }

    public void clear() {
        userMap.clear();
    }
}
