package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    @NotNull
    private long id;
    @Email
    private String email;
    @Pattern(regexp = "^\\S*$")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;

    private Set<Long> friends = new TreeSet<>();

    public void addFriend(long id) { friends.add(id); }

    public void deleteFriend(long id) {
        friends.remove(id);
    }

    public List<Long> getFriends() {
        List<Long> fr = List.copyOf(friends);
        return fr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
