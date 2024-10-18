package com.demo.rest.configuration.listener;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.entity.Genre;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.user.entity.User;
import com.demo.rest.user.service.UserService;
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
    private final AuthorService authorService;
    private final BookService bookService;
    private final UserService userService;

    private final RequestContextController requestContextController;

    @Inject
    public DataInitialization(AuthorService authorService, BookService bookService, UserService userService, RequestContextController requestContextController) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.userService = userService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private void init() {
        requestContextController.activate();
        Author Tolkien = Author.builder()
                .id(UUID.fromString("da55178f-0320-415b-b3d7-a838c6ecb2aa"))
                .name("J.R.R. Tolkien")
                .nationality("Briton")
                .build();

        Author Glukhovsky = Author.builder()
                .id(UUID.fromString("df4c087d-650d-4669-a040-4746c42c6ac5"))
                .name("Dmitry Glukhovsky")
                .nationality("British")
                .build();

        Author Sapkowski = Author.builder()
                .id(UUID.fromString("91fc85ad-0ec4-484f-8436-4be62350180f"))
                .name("Andrzej Sapkowski")
                .nationality("Pole")
                .build();

        Author Clear = Author.builder()
                .id(UUID.fromString("24a84717-c36c-42c1-b69c-c63546d4449d"))
                .name("James Clear")
                .nationality("American")
                .build();

        authorService.create(Tolkien);
        authorService.create(Glukhovsky);
        authorService.create(Sapkowski);
        authorService.create(Clear);

        Book LOTR = Book.builder()
                .id(UUID.fromString("c8bd0b54-3e08-4722-a12b-0d6ee72e05ff"))
                .title("The Lord of The Rings")
                .releaseDate(LocalDate.of(2024, 12,12))
                .genre(Genre.FANTASY)
                .build();

        Book Metro = Book.builder()
                .id(UUID.fromString("c1346fc2-2d0e-4468-a705-aad53f3b3f97"))
                .title("Metro 2033")
                .releaseDate(LocalDate.of(2024, 12,12))
                .genre(Genre.FANTASY)
                .build();

        Book Wiedzmin = Book.builder()
                .id(UUID.fromString("9a212540-a4c5-4308-be8c-4e0ae88104a2"))
                .title("The Withcer")
                .releaseDate(LocalDate.of(2024, 12,12))
                .genre(Genre.FANTASY)
                .build();

        Book AtomoweNawyki = Book.builder()
                .id(UUID.fromString("7863889b-51ec-49d8-b7ea-2beff3724c3f"))
                .title("Atomic habits")
                .releaseDate(LocalDate.of(2024, 12,12))
                .genre(Genre.TUTORIAL)
                .build();

        bookService.create(LOTR);
        bookService.create(Metro);
        bookService.create(Wiedzmin);
        bookService.create(AtomoweNawyki);

        User Janek = User.builder()
                .id(UUID.fromString("67096e6b-5bf5-47c0-8d1d-f92c99a18858"))
                .username("Janek")
                .emailAddress("janek@gmail.com")
                .build();

        User Oskar = User.builder()
                .id(UUID.fromString("73a39da9-790f-415e-a4bd-1cf3aa641185"))
                .username("Oskar")
                .emailAddress("oskar@gmail.com")
                .build();

        User Michal = User.builder()
                .id(UUID.fromString("e6b416b3-5b6e-4303-b423-16ab1de3afc1"))
                .username("Michal")
                .emailAddress("Michal@gmail.com")
                .build();

        User Kacper = User.builder()
                .id(UUID.fromString("80ed5ffb-a67a-4253-910c-a64134a01ccf"))
                .username("Kacper")
                .emailAddress("kacper@gmail.com")
                .build();

        userService.create(Janek);
        userService.create(Oskar);
        userService.create(Michal);
        userService.create(Kacper);

        requestContextController.deactivate();
    }
}
