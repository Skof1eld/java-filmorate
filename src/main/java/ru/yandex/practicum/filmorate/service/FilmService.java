package ru.yandex.practicum.filmorate.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.*;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;

    public void create(Film film) {
        validateFilmMpaAndGenres(film);
        filmStorage.create(film);
    }

    public Film update(Film film) {
        if (filmStorage.findFilmById(film.getId()).isEmpty()) {
            throw new FilmNotFoundException(String.format("Фильм с таким ID %d не найден", film.getId()));
        }
        validateFilmMpaAndGenres(film);
        return filmStorage.update(film);
    }

    public Film findFilmById(Long id) {
        Film film = filmStorage.findFilmById(id).orElseThrow(()
                -> new FilmNotFoundException(String.format("Фильм с таким ID %d не найден", id)));
        genreStorage.findAllGenresByFilm(List.of(film));
        return film;
    }

    public List<Film> findAllFilms() {
        List<Film> films = filmStorage.findAllFilms();
        genreStorage.findAllGenresByFilm(films);
        return films;
    }

    public void addLike(Long id, Long userId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(userId).isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с таким ID %d не найден", id));
        }
        likeStorage.addLike(id, userId);
    }

    public void removeLike(Long id, Long userId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(userId).isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с таким ID %d не найден", id));
        }
        likeStorage.removeLike(id, userId);
    }

    private void validateFilmMpaAndGenres(Film film) {
        if (film.getMpa() == null) {
            throw new ValidationException("Рейтинг-Mpa обязателен для фильма");
        }

        mpaStorage.findMpaById(film.getMpa().getId())
                .orElseThrow(() -> new MpaNotFoundException("Рейтинг-MPA не найден"));

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            List<Long> idGenre = film.getGenres().stream()
                    .map(Genre::getId)
                    .toList();

            List<Genre> genreFound = genreStorage.findGenresByIdList(idGenre);

            if (genreFound.size() != idGenre.size()) {
                throw new GenreNotFoundException("Один или несколько жанров не найдены!");
            }
        }
    }

    public Genre findGenreById(Long id) {
        return genreStorage.findGenreById(id).orElseThrow(() -> new GenreNotFoundException("Жанр не найден."));
    }

    public List<Film> findPopular(Long count) {
        List<Film> films = filmStorage.findPopular(count);
        genreStorage.findAllGenresByFilm(films);
        return films;
    }

    public List<Mpa> findAllMpa() {
        return mpaStorage.findAllMpa();
    }

    public Mpa findMpaById(Long id) {
        return mpaStorage.findMpaById(id).orElseThrow(() -> new MpaNotFoundException("Рейтинг-MPA не найден."));
    }

    public List<Genre> findAllGenres() {
        return genreStorage.findAllGenres();
    }
}
