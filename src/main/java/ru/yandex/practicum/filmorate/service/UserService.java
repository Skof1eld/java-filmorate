package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public void create(User user) {
        validate(user);
        userStorage.create(user);
    }

    public User update(User user) {
        validate(user);
        if (userStorage.findUserById(user.getId()).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        return userStorage.update(user);
    }

    public User findUserById(Long id) {
        return userStorage.findUserById(id).orElseThrow(()
                -> new UserNotFoundException(String.format("Пользователь с таким ID %d не найден", id)));
    }

    public void addFriend(Long id, Long friendId) {
        if (id < 0 || friendId < 0 || userStorage.findUserById(id).isEmpty()
                || userStorage.findUserById(friendId).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        friendStorage.addFriend(id, friendId);
    }

    public List<User> findAllFriends(Long id) {
        userStorage.findUserById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        return friendStorage.findAllFriends(id);
    }

    public List<User> findCommonFriends(Long id, Long otherId) {
        return friendStorage.findCommonFriends(id, otherId);
    }

    public void removeFriend(Long id, Long friendId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(friendId).isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с таким ID %d не найден", id));
        }
        friendStorage.removeFriend(id, friendId);
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
