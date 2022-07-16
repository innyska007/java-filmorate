package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public void addLikes(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        Set<Long> likes = film.getLikes();
        likes.add(userId);
        film.setLikes(likes);
    }

    public void deleteLikes(long filmId, long userId) {
        User user = userStorage.getUserById(userId);
        Film film = filmStorage.getFilmById(filmId);
        Set<Long> likes = film.getLikes();
        likes.remove(userId);
        film.setLikes(likes);
    }

    public List<Film> getPopularFilm(int count) {
        List<Film> likes =
                filmStorage.getAllFilms().stream()
                        .sorted(comparing(Film::getRate).reversed())
                        .limit(count)
                        .collect(toList());
        return likes;
    }
}
