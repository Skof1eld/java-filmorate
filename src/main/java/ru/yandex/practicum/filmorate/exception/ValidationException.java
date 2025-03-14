package ru.yandex.practicum.filmorate.exception;

/**
 * ValidationException.
 */
public class ValidationException extends RuntimeException {

    /**
     * Создаем исключение с сообщением об ошибки.
     *
     * @param message - описание ошибки
     */
    public ValidationException(final String message) {
        super(message);
    }
}
