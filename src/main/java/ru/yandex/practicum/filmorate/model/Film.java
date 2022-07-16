package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Film {
    @NotNull
    private long id;
    @NotBlank
    private String name;
    @Size(min=1, max=200)
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

    private Set<Long> likes = new TreeSet<>();

    public int getRate() {
        return likes.size();
    }
}
