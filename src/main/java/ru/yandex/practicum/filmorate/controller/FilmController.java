package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmStorage.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film findFilm(@PathVariable("id") Integer id) {
        return filmStorage.getFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> findPopular(@RequestParam(defaultValue = "10", required = false) Integer count) {
        return filmService.getPopularFilm(count);
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Map<String, String> pathVarsMap) {
        Long id = Long.valueOf(pathVarsMap.get("id"));
        Long userId = Long.valueOf(pathVarsMap.get("userId"));
        filmService.addLikes(id, userId);
    }

    @DeleteMapping("/films")
    public void delete(@Valid @RequestBody Film film) {
        filmStorage.delete(film);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Map<String, String> pathVarsMap) {
        Long id = Long.valueOf(pathVarsMap.get("id"));
        Long userId = Long.valueOf(pathVarsMap.get("userId"));
        filmService.deleteLikes(id, userId);
    }
}
