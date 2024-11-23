package com.demo.rest.configuration.listener;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.entity.Genre;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.user.entity.User;
import com.demo.rest.user.entity.UserRoles;
import com.demo.rest.user.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.servlet.ServletContextListener;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})
@RunAs("admin")
public class DataInitialization {
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

    @PostConstruct
    @SneakyThrows
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
                    .login("Janek")
                    .emailAddress("janek@gmail.com")
                    .password("janekPassword")
                    .roles(List.of(UserRoles.USER, UserRoles.ADMIN))
                    .build();

            User Oskar = User.builder()
                    .id(UUID.randomUUID())
                    .username("Oskar")
                    .login("Oskar")
                    .emailAddress("oskar@gmail.com")
                    .password("oskarPassword")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User Michal = User.builder()
                    .id(UUID.randomUUID())
                    .username("Michal")
                    .login("Michal")
                    .emailAddress("Michal@gmail.com")
                    .password("michalPassword")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User Kacper = User.builder()
                    .id(UUID.randomUUID())
                    .username("Kacper")
                    .login("Kacper")
                    .emailAddress("kacper@gmail.com")
                    .password("kacperPassword")
                    .roles(List.of(UserRoles.USER))
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
