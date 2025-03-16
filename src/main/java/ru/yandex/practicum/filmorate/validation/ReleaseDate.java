package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * Валидатор для аннотации @CheckReleaseDate
 */
public class ReleaseDate implements ConstraintValidator<CheckReleaseDate, LocalDate> {
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return date != null && !date.isBefore(MIN_RELEASE_DATE);
    }
}
