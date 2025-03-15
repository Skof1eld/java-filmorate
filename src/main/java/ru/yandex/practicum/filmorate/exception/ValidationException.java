package ru.yandex.practicum.filmorate.exception;

/**
 * ValidationException.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
        super(message);
    }
}
