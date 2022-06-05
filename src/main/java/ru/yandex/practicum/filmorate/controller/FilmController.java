package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.filmpredicate.FilmPredicate;
import ru.yandex.practicum.filmorate.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FilmController {
    @Autowired
    private List<FilmPredicate> filmValidators;
    private Map<Integer, Film> films = new HashMap<>();

    private static int id = 1;

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/films")
    public List<Film> findAll() {
        List<Film> allFilms = new ArrayList<>();
        for(Film f : films.values()) {
            allFilms.add(f);
        }
        return allFilms;
    }

    public void clear() {
        films.clear();
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        final var filmErrorValidator = filmValidators.stream()
                .filter(validator -> !validator.test(film))
                .findFirst();
        filmErrorValidator.ifPresent(validator -> {
            log.error(validator.getErrorMessage());
            throw new ValidationException(validator.getErrorMessage());
        });
            film.setId(id);
            id++;
            films.put(film.getId(), film);
            log.info("Добавление фильма прошло успешно");
            return film;
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        final var filmErrorValidator = filmValidators.stream()
                .filter(validator -> !validator.test(film))
                .findFirst();
        filmErrorValidator.ifPresent(validator -> {
            log.error(validator.getErrorMessage());
            throw new ValidationException(validator.getErrorMessage());
        });
        if(film.getId() > 0 && films.containsKey(film.getId())) {
            films.replace(film.getId(), film);
            log.info("Обновление фильма прошло успешно");
            return film;
        } else  {
            throw new NotFoundException("Фильма с таким id не существует");
        }
    }

}
