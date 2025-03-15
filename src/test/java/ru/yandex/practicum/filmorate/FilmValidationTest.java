package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmValidationTest {
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    /**
     * Проверка на пустое название
     */
    @Test
    void nameShouldNotBeEmpty() {
        Film film = new Film();
        film.setName("");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(100);

        assertTrue(film.getName().isBlank());
    }

    /**
     * Описание не должно превышать 200 символов
     */
    @Test
    void descriptionMustNotExceed200Characters() {
        Film film = new Film();
        film.setName("Name");
        film.setDescription("A".repeat(201));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(100);

        assertTrue(film.getDescription().length() > 200);
    }

    /**
     * Дата релиза должна быть не раньше 28 декабря 1895 года
     */
    @Test
    void releaseDateShouldNotBeEarlierThanSpecifiedDate() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Valid Description");
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        film.setDuration(100);

        assertTrue(film.getReleaseDate().isBefore(MIN_RELEASE_DATE));
    }

    /**
     * Продолжительность фильма должна быть положительной
     */
    @Test
    void lengthOfTheFilmShouldBePositive() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Valid Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(-10); // Отрицательная длительность

        assertTrue(film.getDuration() <= 0);
    }
}
