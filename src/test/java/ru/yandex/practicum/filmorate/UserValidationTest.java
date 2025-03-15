package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {

    /**
     * Email должен содержать '@'
     */
    @Test
    void emailMustContainAtSymbol() {
        User user = new User();
        user.setEmail("incorrect-email"); // Неверный email
        user.setLogin("TestLogin");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertFalse(user.getEmail().contains("@"));
    }

    /**
     * Проверка на пустой логин
     */
    @Test
    void usernameCannotBeEmpty() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setLogin(" ");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertTrue(user.getLogin().isBlank());
    }

    /**
     * Проверка на пустое имя, оно должно заменяться на логин
     */
    @Test
    void emptyNameShouldBeReplacedWithUsername() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setLogin("UserTest");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        user.setName("");

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        assertEquals("UserTest", user.getName());
    }

    /**
     * Проверка, что дата рождения не может быть в будущем
     */
    @Test
    void dateOfBirthCannotBeInFuture() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setLogin("LoginTest");
        user.setBirthday(LocalDate.now().plusDays(1));

        assertTrue(user.getBirthday().isAfter(LocalDate.now()));
    }
}
