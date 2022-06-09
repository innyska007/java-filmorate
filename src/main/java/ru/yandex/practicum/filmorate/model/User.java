package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import javax.validation.constraints.*;

@Data
public class User {
    @NotNull
    private int id;
    @Email
    private String email;
    @Pattern(regexp = "^\\S*$")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}
