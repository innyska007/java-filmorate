package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import ru.yandex.practicum.filmorate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.userpredicate.UserPredicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private List<UserPredicate> userValidators;

    private Map<Integer, User> users = new HashMap<>();

    private static int id = 1;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public List<User> findAll() {
        List<User> allUsers = new ArrayList<>();
        for(User u : users.values()) {
            allUsers.add(u);
        }
        return allUsers;
    }

    public void clear() {
        users.clear();
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        final var userErrorValidator = userValidators.stream()
                .filter(validator -> !validator.test(user))
                .findFirst();
        userErrorValidator.ifPresent(validator -> {
            log.error(validator.getErrorMessage());
            throw new ValidationException(validator.getErrorMessage());
        });
        if(user.getName().isEmpty()) { user.setName(user.getLogin()); }
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        log.info("Добавление пользователя прошло успешно");
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        final var userErrorValidator = userValidators.stream()
                .filter(validator -> !validator.test(user))
                .findFirst();
        userErrorValidator.ifPresent(validator -> {
            log.error(validator.getErrorMessage());
            throw new ValidationException(validator.getErrorMessage());
        });
        if(user.getId() > 0 && users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
            log.info("Обновление пользователя прошло успешно");
            return user;
        } else  {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
    }
}
