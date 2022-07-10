package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage{

    private Map<Long, Film> films = new HashMap<>();

    private static long id = 1;

    @Override
    public Film create(Film film) {
        validate(film);
        film.setId(id);
        id++;
        films.put(film.getId(), film);
        log.info("film creating was successful");
        return film;
    }

    @Override
    public Film update(Film film) {
        validate(film);
        if (film.getId() > 0 && films.containsKey(film.getId())) {
            films.replace(film.getId(), film);
            log.info("film updating was successful");
            return film;
        } else {
            throw new NotFoundException("film doesn't exist");
        }
    }

    @Override
    public void validate(Film film){
        if(film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("release date must not be before 28.12.1895");
        } else if (film.getDuration().isNegative()) {
            throw new ValidationException("duration must not be negative");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        List<Film> allFilms = new ArrayList<>();
        for(Film f : films.values()) {
            allFilms.add(f);
        }
        return allFilms;
    }

    @Override
    public void delete(Film film) {
        films.remove(film.getId());
    }

    @Override
    public Film getFilmById(long id) {
        if(films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundException("film doesn't exist");
        }
    }
}
