package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("films")
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("POST / film / {}", film.getName());
        filmService.create(film);
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("PUT / film / {}", film.getName());
        filmService.update(film);
        return film;
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable("id") Long id) {
        log.info("GET / {}", id);
        return filmService.findFilmById(id);
    }

    @GetMapping
    public List<Film> findAllFilms() {
        log.info("GET / films");
        return filmService.findAllFilms();
    }

    @GetMapping("/popular")
    public List<Film> findPopular(@RequestParam(defaultValue = "10") @Positive Long count) {
        log.info("GET / popular");
        return filmService.findPopular(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addFilmLike(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        log.info("PUT / {} / like / {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeFilmLike(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        log.info("DELETE / {} / like / {}", id, userId);
        filmService.removeLike(id, userId);
    }
}
