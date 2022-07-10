package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriends(long userId, long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
    }

    public void deleteFriends(long userId, long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
    }

    public List<User> getUserFriends(long userId) {
        List<User> friends = new ArrayList<>();
        for(Long l : userStorage.getUserById(userId).getFriends()) {
            friends.add(userStorage.getUserById(l));
        }
        return friends;
    }

    public List<User> getCommonUsersFriends(long userId, long otherId) {
        return getUserFriends(userId).stream().filter(getUserFriends(otherId)::contains).collect(Collectors.toList());
    }
}
