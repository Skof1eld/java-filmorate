package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class})
class FilmoRateApplicationTest {
    private final UserDbStorage userStorage;

    @Test
    public void testFindUserById() {

        User user = User.builder()
                .email("test@mail.ru")
                .login("LoginTest")
                .name("TestName")
                .birthday(LocalDate.of(1987, 3, 6))
                .build();
        User createdUser = userStorage.create(user);

        Optional<User> userOptional = userStorage.findUserById(createdUser.getId());

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("id", createdUser.getId())
                );
    }
}
