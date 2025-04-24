package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long idCounter = 1;

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film create(Film film) {
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        film.setFilm_id(idCounter++);
        films.put(film.getFilm_id(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getFilm_id())) {
            throw new NotFoundException("Фильм не найден");
        }
        films.put(film.getFilm_id(), film);
        return film;
    }

    @Override
    public Film getById(long id) {
        return films.get(id);
    }
}
