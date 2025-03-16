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

import java.util.*;

/**
 * Film Controller.
 */
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
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
        validateFilm(oldFilm, newFilm);

        log.info("Обновлен фильм: {}", oldFilm);
        return oldFilm;
    }

    public void validateFilm(Film oldFilm, Film newFilm) {
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
