package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.CheckReleaseDate;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс представляет фильм в системе FilmoRate.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @NotNull
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;


    @NotNull(message = "Дата релиза не может быть пустой")
    @CheckReleaseDate
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;

    @NotNull
    private Mpa mpa;

    private final LinkedHashSet<Genre> genres = new LinkedHashSet<>();
    private final Set<Long> likes = new HashSet<>();
}
