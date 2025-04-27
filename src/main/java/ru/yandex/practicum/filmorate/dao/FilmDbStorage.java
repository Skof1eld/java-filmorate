package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
@Primary
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_FILMS = "SELECT f.film_id, f.name, f.description, f.releaseDate, f.duration, " +
            "mpa.rating_id, mpa.name AS mpa_name " +
            "FROM films AS f " +
            "INNER JOIN mpa_rating AS mpa ON f.rating_id = mpa.rating_id ";

    @Override
    public Film create(Film film) {
        String sql = "INSERT INTO films (name, description, releaseDate, duration, rating_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"film_id"});
                    ps.setString(1, film.getName());
                    ps.setString(2, film.getDescription());
                    ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                    ps.setLong(4, film.getDuration());
                    ps.setLong(5, film.getMpa().getId());
                    return ps;
                }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        updateGenres(film.getGenres(), film.getId());
        return film;
    }

    @Override
    public Film update(Film film) {
        Long id = film.getId();
        String sql = "UPDATE films SET name = ?, description = ?, releaseDate = ?, duration = ?, rating_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), id);
        updateGenres(film.getGenres(), id);
        return film;
    }

    @Override
    public Optional<Film> findFilmById(Long id) {
        String sql = "WHERE f.film_id = ?";
        return jdbcTemplate.query(SELECT_FILMS + sql, (rs, rowNum) -> createFilm(rs), id).stream().findFirst();
    }

    @Override
    public List<Film> findAllFilms() {
        String sql = "ORDER BY f.film_id";
        return jdbcTemplate.query(SELECT_FILMS + sql, (rs, rowNum) -> createFilm(rs));
    }

    @Override
    public List<Film> findPopular(Long count) {
        String sql = "LEFT JOIN likes ON f.film_id = likes.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(likes.film_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(SELECT_FILMS + sql, (rs, rowNum) -> createFilm(rs), count);
    }

    private Film createFilm(ResultSet rs) throws SQLException {
        Long id = rs.getLong("film_id");
        Film film = Film.builder()
                .id(id)
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("releaseDate").toLocalDate())
                .duration(rs.getLong("duration"))
                .mpa(new Mpa(rs.getLong("rating_id"), rs.getString("mpa_name")))
                .build();
        return film;
    }

    private void updateGenres(Set<Genre> genres, Long id) {
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id = ?", id);
        if (!genres.isEmpty()) {
            String sql = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
            Genre[] g = genres.toArray(new Genre[genres.size()]);
            jdbcTemplate.batchUpdate(
                    sql,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, id);
                            ps.setLong(2, g[i].getId());
                        }

                        public int getBatchSize() {
                            return genres.size();
                        }
                    });
        }
    }
}
