package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import java.util.*;

/**
 * User Controller.
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Пользователь добавлен: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {

        if (!users.containsKey(newUser.getId())) {
            throw new ValidationException("Id пользователя не найден");
        }

        User oldUser = users.get(newUser.getId());
        if (newUser.getEmail() != null && !newUser.getEmail().equals(oldUser.getEmail())) {
            oldUser.setEmail(newUser.getEmail());
        }

        if (newUser.getLogin() != null && !newUser.getLogin().equals(oldUser.getLogin())) {
            oldUser.setLogin(newUser.getLogin());
        }

        if (newUser.getName() != null && !newUser.getName().equals(oldUser.getName())) {
            oldUser.setName(newUser.getName());
        }

        if (newUser.getBirthday() != null && !newUser.getBirthday().equals(oldUser.getBirthday())) {
            oldUser.setBirthday(newUser.getBirthday());
        }

        log.info("Данные пользователя обновлены: {}", oldUser);
        return oldUser; // Возвращаем обновленного пользователя
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
