package ru.yandex.practicum.filmorate.userpredicate;

import ru.yandex.practicum.filmorate.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserLoginValidator implements UserPredicate {
    @Override
    public boolean test(User user) {
        return (!user.getLogin().isBlank() && !user.getLogin().contains(" "));
    }

    @Override
    public String getErrorMessage(){
        return "Логин должен быть не пустым и не содержать пробелов";
    }
}
