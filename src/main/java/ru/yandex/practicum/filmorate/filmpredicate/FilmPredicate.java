package ru.yandex.practicum.filmorate.filmpredicate;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.function.Predicate;

public interface FilmPredicate extends Predicate<Film> {
    String getErrorMessage();
}
