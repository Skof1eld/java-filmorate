package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        user.setUser_id(idCounter++);
        users.put(user.getUser_id(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getUser_id())) {
            throw new NotFoundException("Пользователь не найден");
        }
        users.put(user.getUser_id(), user);
        return user;
    }

    @Override
    public User getById(long id) {
        return users.get(id);
    }
}
