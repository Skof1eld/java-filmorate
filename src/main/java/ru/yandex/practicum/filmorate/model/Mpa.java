package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс представляет рейтинг-MPA в системе FilmoRate.
 * - Ассоциация MPAA (Американская Киноассоциация, Motion Picture Association of America)
 * является родоначальницей рейтинговой системы, помогающей родителям оценить, подходят ли те
 * или иные фильмы для просмотра их детьми. Эта система не берёт на себя функции критика, она не определяет,
 * плоха или хороша та или иная картина. Она не подвергает её цензуре, а лишь разъясняет
 * потенциальные опасности для детских глаз
 */

@Data
@AllArgsConstructor
public class Mpa {
    private Long id;
    private String name;
}
