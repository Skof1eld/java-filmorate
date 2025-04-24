package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        generateName(user);
        user.setLogin(user.getLogin().trim());
        return userStorage.create(user);
    }

    public User update(User user) {
        getUserById(user.getUser_id());
        generateName(user);
        return userStorage.update(user);
    }

    public User getById(long id) {
        return getUserById(id);
    }

    public void addFriend(long userId, long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        userStorage.update(user);
        userStorage.update(friend);
    }

    public void removeFriend(long userId, long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        userStorage.update(user);
        userStorage.update(friend);
    }

    public Collection<User> getFriends(long userId) {
        User user = getUserById(userId);
        List<User> friends = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            friends.add(getUserById(friendId));
        }
        return friends;
    }

    public Collection<User> getCommonFriends(long userId, long otherId) {
        User user = getUserById(userId);
        User otherUser = getUserById(otherId);
        Set<Long> commonFriendIds = new HashSet<>(user.getFriends());
        commonFriendIds.retainAll(otherUser.getFriends());

        List<User> commonFriends = new ArrayList<>();
        for (Long friendId : commonFriendIds) {
            commonFriends.add(getUserById(friendId));
        }
        return commonFriends;
    }

    private User getUserById(long id) {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с таким ID %d не найден", id));
        }
        return user;
    }

    private void generateName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
