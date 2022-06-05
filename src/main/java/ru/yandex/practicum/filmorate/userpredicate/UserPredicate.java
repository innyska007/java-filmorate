package ru.yandex.practicum.filmorate.userpredicate;

import ru.yandex.practicum.filmorate.model.User;

import java.util.function.Predicate;

public interface UserPredicate extends Predicate<User> {
    String getErrorMessage();
}
