package ru.yandex.practicum.filmorate.userpredicate;

import ru.yandex.practicum.filmorate.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserBirthdayValidator implements UserPredicate {
    @Override
    public boolean test(User user) {
        return !user.getBirthday().isAfter(LocalDate.now());
    }

    @Override
    public String getErrorMessage() {
        return "Дата рождения указана неверно";
    }
}
