package ru.yandex.practicum.filmorate.filmpredicate;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Service;

@Service
public class FilmDescriptionValidator implements FilmPredicate{
    @Override
    public boolean test(Film film) {
        return film.getDescription().length() < 200;
    }

    @Override
    public String getErrorMessage(){
        return "Описание не должно содержать больше 200 знаков";
    }
}
