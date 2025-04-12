package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FilmValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Проверка на пустое название
     */
    @Test
    void nameShouldNotBeEmpty() {
        Film film = new Film();
        film.setName("");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
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

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
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

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
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
        film.setDuration(-10);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void checksTthePossibilityAddingLikesAndTheirStorage() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Valid Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(100);

        film.getLikes().add(1L);
        film.getLikes().add(2L);

        assertThat(film.getLikes()).hasSize(2).contains(1L, 2L);
    }

    @Test
    void checksThatTheNewMovieHasNoLikes() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Valid Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(100);

        assertThat(film.getLikes()).isEmpty();
    }
}
