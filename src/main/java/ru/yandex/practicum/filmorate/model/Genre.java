package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс представляет жанры в системе FilmoRate.
 */

@Data
@AllArgsConstructor
public class Genre {
    private Long id;
    private String name;
}
