package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FilmorateApplicationTests {
    @LocalServerPort
    private int port;

    private Film film;
    private User user;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FilmController filmController;

    @Autowired
    private UserController userController;

    @AfterEach
    private void clear() {
        filmController.clear();
        userController.clear();
    }

    @Test
    public void shouldCreateFilm() throws Exception {
        film = new Film();
        film.setName("Saw");
        film.setDescription("dlkfjgpg");
        film.setDuration(Duration.ofMinutes(190));
        film.setReleaseDate(LocalDate.of(2020,5,4));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/films",
                film, Film.class);
        assertEquals(1, filmController.findAll().size());

    }

    @Test
    public void shouldNotCreateFilmEmptyName() throws Exception {
        film = new Film();
        film.setName("");
        film.setDescription("dlkfjgpg");
        film.setDuration(Duration.ofMinutes(190));
        film.setReleaseDate(LocalDate.of(2020,5,4));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/films",
                film, Film.class);
        assertEquals(0, filmController.findAll().size());
    }

    @Test
    public void shouldNotCreateFilm200Description() throws Exception {
        film = new Film();
        film.setName("");
        film.setDescription("Класс, который вы добавили в приложение, называется контроллер. Это специальный класс, " +
                "который предназначен для обработки HTTP-запросов от клиента и возвращения результатов. То есть класс " +
                "для создания парапам");
        film.setDuration(Duration.ofMinutes(190));
        film.setReleaseDate(LocalDate.of(2020,5,4));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/films",
                film, Film.class);
        assertEquals(0, filmController.findAll().size());
    }

    @Test
    public void shouldNotCreateFilmWrongDate() throws Exception {
        film = new Film();
        film.setName("");
        film.setDescription("dlkfjgpg");
        film.setDuration(Duration.ofMinutes(190));
        film.setReleaseDate(LocalDate.of(1895,12,27));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/films",
                film, Film.class);
        assertEquals(0, filmController.findAll().size());
    }

    @Test
    public void shouldNotCreateFilmNegativeDuration() throws Exception {
        film = new Film();
        film.setName("");
        film.setDescription("dlkfjgpg");
        film.setDuration(Duration.ofMinutes(-190));
        film.setReleaseDate(LocalDate.of(2020,5,4));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/films",
                film, Film.class);
        assertEquals(0, filmController.findAll().size());
    }

    @Test
    public void shouldCreateUser() throws Exception {
        user = new User();
        user.setName("Inna");
        user.setLogin("innyska007");
        user.setEmail("innyska007@list.ru");
        user.setBirthday(LocalDate.of(1984,5,31));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/users",
                user, User.class);
        assertEquals(1, userController.findAll().size());

    }

    @Test
    public void shouldCreateUserEmptyName() throws Exception {
        user = new User();
        user.setName("");
        user.setLogin("innyska007");
        user.setEmail("innyska007@list.ru");
        user.setBirthday(LocalDate.of(1984,5,31));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/users",
                user, User.class);
        assertEquals(1, userController.findAll().size());
        User user1 = userController.findAll().get(0);
        assertEquals("innyska007", user1.getName());
    }

    @Test
    public void shouldNotCreateUserEmptyLogin() throws Exception {
        user = new User();
        user.setName("Inna");
        user.setLogin("innyska 007");
        user.setEmail("innyska007@list.ru");
        user.setBirthday(LocalDate.of(1984,5,31));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/users",
                user, User.class);
        assertEquals(0, userController.findAll().size());
        user.setLogin("");
        assertEquals(0, userController.findAll().size());
    }

    @Test
    public void shouldNotCreateUserEmptyEmail() throws Exception {
        user = new User();
        user.setName("Inna");
        user.setLogin("innyska007");
        user.setEmail("innyska007list.ru");
        user.setBirthday(LocalDate.of(1984,5,31));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/users",
                user, User.class);
        assertEquals(0, userController.findAll().size());
        user.setEmail("");
        assertEquals(0, userController.findAll().size());
    }

    @Test
    public void shouldNotCreateUserFutureBirthday() throws Exception {
        user = new User();
        user.setName("Inna");
        user.setLogin("innyska007");
        user.setEmail("innyska007@list.ru");
        user.setBirthday(LocalDate.of(2023,5,31));
        final Object o = this.restTemplate.postForObject("http://localhost:" + port + "/users",
                user, User.class);
        assertEquals(0, userController.findAll().size());
    }
}
