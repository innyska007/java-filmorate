package ru.yandex.practicum.filmorate.userpredicate;

import ru.yandex.practicum.filmorate.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserEmailValidator implements UserPredicate {
    @Override
    public boolean test(User user) {
        return (!user.getEmail().isBlank() && user.getEmail().contains("@"));
    }

    @Override
    public String getErrorMessage(){
        return "Электронная почта заполнена неверно";
    }
}
