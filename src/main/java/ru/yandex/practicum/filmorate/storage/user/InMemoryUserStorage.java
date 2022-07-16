package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage{
    private Map<Long, User> users = new HashMap<>();

    private static long id = 1;

    @Override
    public User create(User user) {
        if(user.getName().isEmpty()) { user.setName(user.getLogin()); }
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        log.info("user creating was successful");
        return user;
    }

    @Override
    public User update(User user) {
        if(users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
            log.info("user updating was successful");
            return user;
        } else  {
            throw new NotFoundException("user doesn't exist");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for(User f : users.values()) {
            allUsers.add(f);
        }
        return allUsers;
    }

    @Override
    public void delete(User user) {
        long id = user.getId();
        users.remove(id);
    }

    @Override
    public User getUserById(long id) {
        if(users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new NotFoundException("user doesn't exist");
        }
    }
}
