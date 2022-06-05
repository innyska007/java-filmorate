package ru.yandex.practicum.filmorate.filmpredicate;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Service;

@Service
public class FilmNameValidator implements FilmPredicate {
    @Override
    public boolean test(Film film) {
        return !film.getName().isBlank();
    }

    @Override
    public String getErrorMessage(){
        return "Название фильма не должно быть пустым";
    }
}
