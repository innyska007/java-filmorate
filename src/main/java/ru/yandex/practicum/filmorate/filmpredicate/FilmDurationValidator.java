package ru.yandex.practicum.filmorate.filmpredicate;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Service;

@Service
public class FilmDurationValidator implements FilmPredicate {
    @Override
    public boolean test(Film film) {
        return !film.getDuration().isNegative();
    }

    @Override
    public String getErrorMessage(){
        return "Продолжительность фильма не должна быть отрицательной";
    }
}
