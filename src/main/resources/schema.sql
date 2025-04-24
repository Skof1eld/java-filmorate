-- Таблицас рейтингом
CREATE TABLE IF NOT EXISTS ratings (
    rating_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Таблица с жанром
CREATE TABLE IF NOT EXISTS genre (
    genre_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Таблица с фильмами
CREATE TABLE IF NOT EXISTS films (
    film_id BIGINT PRIMARY KEY,
    rating_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description CHAR(200),
    duration INTEGER,
    release_date DATE NOT NULL,
    FOREIGN KEY (rating_id) REFERENCES ratings(rating_id)
);

-- Таблица с пользователями
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    login VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL
);

-- Таблица с лайками
CREATE TABLE IF NOT EXISTS likes (
    user_id BIGINT,
    film_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id)
);

-- Таблица с друзьями
CREATE TABLE IF NOT EXISTS friends (
    user_id BIGINT,
    friend_id BIGINT,
    status_friends BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (friend_id) REFERENCES users(user_id)
);

-- Таблица для связи фильмов с жанрами
CREATE TABLE IF NOT EXISTS film_genre (
    film_id BIGINT,
    genre_id BIGINT,
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    FOREIGN KEY (genre_id) REFERENCES genre(genre_id)
);
