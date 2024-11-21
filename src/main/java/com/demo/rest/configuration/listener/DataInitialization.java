package com.demo.rest.configuration.listener;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.entity.Genre;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.user.entity.User;
import com.demo.rest.user.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextListener;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class DataInitialization implements ServletContextListener {
    private AuthorService authorService;
    private BookService bookService;
    private UserService userService;

    @EJB
    private void setAuthorService(AuthorService service) {
        this.authorService = service;
    }

    @EJB
    private void setBookService(BookService service) {
        this.bookService = service;
    }

    @EJB
    private void setUserService(UserService service) {
        this.userService = service;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @PostConstruct
    private void init() {
        if (userService.find("Janek").isEmpty()) {
            Author Tolkien = Author.builder()
                    .id(UUID.randomUUID())
                    .name("J.R.R. Tolkien")
                    .nationality("Briton")
                    .build();

            Author Glukhovsky = Author.builder()
                    .id(UUID.randomUUID())
                    .name("Dmitry Glukhovsky")
                    .nationality("Russian")
                    .build();

            Author Sapkowski = Author.builder()
                    .id(UUID.randomUUID())
                    .name("Andrzej Sapkowski")
                    .nationality("Pole")
                    .build();

            Author Clear = Author.builder()
                    .id(UUID.randomUUID())
                    .name("James Clear")
                    .nationality("American")
                    .build();

            authorService.create(Tolkien);
            authorService.create(Glukhovsky);
            authorService.create(Sapkowski);
            authorService.create(Clear);

            User Janek = User.builder()
                    .id(UUID.randomUUID())
                    .username("Janek")
                    .emailAddress("janek@gmail.com")
                    .build();

            User Oskar = User.builder()
                    .id(UUID.randomUUID())
                    .username("Oskar")
                    .emailAddress("oskar@gmail.com")
                    .build();

            User Michal = User.builder()
                    .id(UUID.randomUUID())
                    .username("Michal")
                    .emailAddress("Michal@gmail.com")
                    .build();

            User Kacper = User.builder()
                    .id(UUID.randomUUID())
                    .username("Kacper")
                    .emailAddress("kacper@gmail.com")
                    .build();

            userService.create(Janek);
            userService.create(Oskar);
            userService.create(Michal);
            userService.create(Kacper);

            Book LOTR = Book.builder()
                    .id(UUID.randomUUID())
                    .title("The Lord of The Rings")
                    .releaseDate(LocalDate.of(2024, 12, 12))
                    .genre(Genre.FANTASY)
                    .author(Tolkien)
                    .user(Janek)
                    .build();

            Book Metro = Book.builder()
                    .id(UUID.randomUUID())
                    .title("Metro 2033")
                    .releaseDate(LocalDate.of(2024, 12, 12))
                    .genre(Genre.FANTASY)
                    .author(Glukhovsky)
                    .user(Oskar)
                    .build();

            Book Wiedzmin = Book.builder()
                    .id(UUID.randomUUID())
                    .title("The Withcer")
                    .releaseDate(LocalDate.of(2024, 12, 12))
                    .genre(Genre.FANTASY)
                    .author(Sapkowski)
                    .user(Michal)
                    .build();

            Book AtomoweNawyki = Book.builder()
                    .id(UUID.randomUUID())
                    .title("Atomic habits")
                    .releaseDate(LocalDate.of(2024, 12, 12))
                    .genre(Genre.TUTORIAL)
                    .author(Clear)
                    .user(Kacper)
                    .build();

            bookService.create(LOTR, Tolkien.getId());
            bookService.create(Metro, Glukhovsky.getId());
            bookService.create(Wiedzmin, Sapkowski.getId());
            bookService.create(AtomoweNawyki, Clear.getId());
        }

    }
}
