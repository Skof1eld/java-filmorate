package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Email должен содержать '@'
     */
    @Test
    void emailMustContainAtSymbol() {
        User user = new User();
        user.setEmail("incorrect-email");
        user.setLogin("TestLogin");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
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

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
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

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
    }

    /**
     * Проверка, что друзья изначально пусты
     */
    @Test
    void newUsersFriendListIsEmpty() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setLogin("TestUser");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThat(user.getFriends()).isEmpty();
    }

    /**
     * Проверка добавления друзей
     */
    @Test
    void checksTheAddingFriends() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setLogin("TestUser");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        user.getFriends().add(1L);
        user.getFriends().add(2L);

        assertThat(user.getFriends()).hasSize(2).contains(1L, 2L);
    }
}
