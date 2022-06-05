package ru.yandex.practicum.filmorate.filmpredicate;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FilmReleaseDateValidator implements FilmPredicate{
    @Override
    public boolean test(Film film) {
        return film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28));
    }

    @Override
    public String getErrorMessage(){
        return "Дата релиза не должна быть ранее 28.12.1895";
    }
}
