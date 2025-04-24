-- Очищаем таблицу
DELETE FROM film_genre;
DELETE FROM friends;
DELETE FROM likes;
DELETE FROM films;
DELETE FROM users;
DELETE FROM genre;
DELETE FROM ratings;
-- Инициализация рейтингов
INSERT INTO ratings (rating_id, name) VALUES (1, 'G');
INSERT INTO ratings (rating_id, name) VALUES (2, 'PG');
INSERT INTO ratings (rating_id, name) VALUES (3, 'PG-13');
INSERT INTO ratings (rating_id, name) VALUES (4, 'R');
INSERT INTO ratings (rating_id, name) VALUES (5, 'NC-17');

-- Инициализация жанров
INSERT INTO genre (genre_id, name) VALUES (1, 'Комедия');
INSERT INTO genre (genre_id, name) VALUES (2, 'Драма');
INSERT INTO genre (genre_id, name) VALUES (3, 'Мультфильм');
INSERT INTO genre (genre_id, name) VALUES (4, 'Триллер');
INSERT INTO genre (genre_id, name) VALUES (5, 'Документальный');
INSERT INTO genre (genre_id, name) VALUES (6, 'Боевик');

-- Создание пользователя как пример
INSERT INTO users (user_id, name, login, email, birthday)
VALUES (1, 'Иван Потемкин', 'ivanPotemkin', 'ivanpot@example.com', '1986-01-01');

-- Создание фильма как пример
INSERT INTO films (film_id, rating_id, name, description, duration, release_date)
VALUES (1, 1, 'Матрица', 'Будущее, в котором реальность, является симуляцией', 136, '1999-03-01');

-- Связь фильма с жанром
INSERT INTO film_genre (film_id, genre_id) VALUES (1, 1);
INSERT INTO film_genre (film_id, genre_id) VALUES (1, 2);

-- Лайк
INSERT INTO likes (user_id, film_id) VALUES (1, 1);

-- Друг
INSERT INTO friends (user_id, friend_id, status_friends) VALUES (1, 1, true);