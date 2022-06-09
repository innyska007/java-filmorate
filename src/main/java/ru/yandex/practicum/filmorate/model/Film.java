package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    @NotNull
    private int id;
    @NotBlank
    private String name;
    @Size(min=1, max=200)
    private String description;
    private LocalDate releaseDate;

    private Duration duration;
}
