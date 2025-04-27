package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс представляет пользователя в системе FilmoRate.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @")
    private String email;

    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "^\\S*$", message = "Логин не может быть пустым или состоять только из пробелов")
    private String login;

    private String name;

    @NotNull
    @PastOrPresent(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

    private final Set<Long> friends = new HashSet<>();
}
