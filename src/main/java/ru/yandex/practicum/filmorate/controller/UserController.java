package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;

    public UserController(UserStorage userStorage, UserService userService) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userStorage.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable("id") Long userId) {
        return userStorage.getUserById(userId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findUserFriends(@PathVariable("id") Long userId) {
        return userService.getUserFriends(userId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable Map<String, String> pathVarsMap) {
        Long id = Long.valueOf(pathVarsMap.get("id"));
        Long otherId = Long.valueOf(pathVarsMap.get("otherId"));
        return userService.getCommonUsersFriends(id, otherId);
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        return userStorage.create(user);
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        return userStorage.update(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void updateFriends(@PathVariable Map<String, String> pathVarsMap) {
        Long id = Long.valueOf(pathVarsMap.get("id"));
        Long friendId = Long.valueOf(pathVarsMap.get("friendId"));
        userService.addFriends(id, friendId);
    }

    @DeleteMapping(value = "/users")
    public void delete(@Valid @RequestBody User user) {
        userStorage.delete(user);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Map<String, String> pathVarsMap) {
        Long id = Long.valueOf(pathVarsMap.get("id"));
        Long friendId = Long.valueOf(pathVarsMap.get("friendId"));
        userService.deleteFriends(id, friendId);
    }
}
