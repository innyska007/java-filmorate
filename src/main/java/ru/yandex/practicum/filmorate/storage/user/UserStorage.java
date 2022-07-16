package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public interface UserStorage {
    public User create(User user);
    public User update(User user);
    public List<User> getAllUsers();
    public void delete(User user);
    public User getUserById(long id);
}
