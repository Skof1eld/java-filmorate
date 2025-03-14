package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

/**
 * Film Controller.
 */
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);


    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        if (!films.containsKey(newFilm.getId())) {
            throw new ValidationException("Такой фильм не найден");
        }
        Film oldFilm = films.get(newFilm.getId());
        if (newFilm.getName() != null && !newFilm.getName().equals(oldFilm.getName())) {
            oldFilm.setName(newFilm.getName());
        }

        if (newFilm.getDescription() != null && !newFilm.getDescription().equals(oldFilm.getDescription())) {
            oldFilm.setDescription(newFilm.getDescription());
        }

        if (newFilm.getReleaseDate() != null && !newFilm.getReleaseDate().equals(oldFilm.getReleaseDate())) {
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
        }

        if (newFilm.getDuration() > 0 && newFilm.getDuration() != oldFilm.getDuration()) {
            oldFilm.setDuration(newFilm.getDuration());
        }

        log.info("Обновлен фильм: {}", oldFilm);
        return oldFilm;
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
