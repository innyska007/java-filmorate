package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private Map<Integer, User> users = new HashMap<>();

    private static int id = 1;

    @RequestMapping(method = RequestMethod.GET)
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

    @RequestMapping(method = RequestMethod.POST)
    public User create(@Valid @RequestBody User user) {
        if(user.getName().isEmpty()) { user.setName(user.getLogin()); }
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        log.info("Добавление пользователя прошло успешно");
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public User update(@Valid @RequestBody User user) {
        if(user.getId() > 0 && users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
            log.info("Обновление пользователя прошло успешно");
            return user;
        } else  {
            throw new NotFoundException("Пользователя с таким id не существует");
        }
    }
}
