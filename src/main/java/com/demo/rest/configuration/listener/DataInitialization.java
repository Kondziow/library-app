package com.demo.rest.configuration.listener;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.entity.Genre;
import com.demo.rest.book.repository.api.AuthorRepository;
import com.demo.rest.book.repository.api.BookRepository;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.user.entity.User;
import com.demo.rest.user.entity.UserRoles;
import com.demo.rest.user.repository.api.UserRepository;
import com.demo.rest.user.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor(force = true)
@DependsOn("InitializeAdminService")
@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})
@RunAs(UserRoles.ADMIN)
@Log
public class DataInitialization {
    private AuthorService authorService;
    private BookService bookService;
    private UserService userService;

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Inject
    public DataInitialization(AuthorRepository authorRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

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
                    .id(UUID.fromString("4fb751fb-1a9d-4a51-8982-ce099f2327e9"))
                    .name("J.R.R. Tolkien")
                    .nationality("Briton")
                    .build();

            Author Glukhovsky = Author.builder()
                    .id(UUID.fromString("0fd0d367-7be3-4b49-a16c-5d0ef71bb894"))
                    .name("Dmitry Glukhovsky")
                    .nationality("Russian")
                    .build();

            Author Sapkowski = Author.builder()
                    .id(UUID.fromString("a072c1b2-7b91-4398-b29d-0daddc9880c4"))
                    .name("Andrzej Sapkowski")
                    .nationality("Pole")
                    .build();

            Author Clear = Author.builder()
                    .id(UUID.fromString("f4ae426d-ca47-418a-a48d-7e90c17a4333"))
                    .name("James Clear")
                    .nationality("American")
                    .build();

            authorService.create(Tolkien);
            authorService.create(Glukhovsky);
            authorService.create(Sapkowski);
            authorService.create(Clear);

//            authorRepository.create(Tolkien);
//            authorRepository.create(Glukhovsky);
//            authorRepository.create(Sapkowski);
//            authorRepository.create(Clear);

            User Janek = User.builder()
                    .id(UUID.fromString("3b1651b9-392c-43ac-b496-7e8ca7a57fa2"))
                    .username("Janek")
                    .login("Janek")
                    .emailAddress("janek@gmail.com")
                    .password("janekPassword")
                    .roles(List.of(UserRoles.USER, UserRoles.ADMIN))
                    .build();

            User Oskar = User.builder()
                    .id(UUID.fromString("9e44a703-5a33-4303-b763-9af76ec8a06c"))
                    .username("Oskar")
                    .login("Oskar")
                    .emailAddress("oskar@gmail.com")
                    .password("oskarPassword")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User Michal = User.builder()
                    .id(UUID.fromString("95614115-9b20-4ff3-96bf-6e9da6f3ae40"))
                    .username("Michal")
                    .login("Michal")
                    .emailAddress("Michal@gmail.com")
                    .password("michalPassword")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User Kacper = User.builder()
                    .id(UUID.fromString("cf8f60ec-cd8c-485e-abd5-16cea3b6cf83"))
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

//            userRepository.create(Janek);
//            userRepository.create(Oskar);
//            userRepository.create(Michal);
//            userRepository.create(Kacper);

            Book LOTR = Book.builder()
                    .id(UUID.fromString("ebbcc865-7395-40ba-b2e5-06fc88c082ed"))
                    .title("The Lord of The Rings")
                    .releaseDate(LocalDate.of(2024, 12, 12))
                    .genre(Genre.FANTASY)
                    .author(Tolkien)
                    .user(Janek)
                    .build();

            Book Metro = Book.builder()
                    .id(UUID.fromString("097f70b1-a697-4969-b30d-13b023229b76"))
                    .title("Metro 2033")
                    .releaseDate(LocalDate.of(2024, 12, 12))
                    .genre(Genre.FANTASY)
                    .author(Glukhovsky)
                    .user(Oskar)
                    .build();

            Book Wiedzmin = Book.builder()
                    .id(UUID.fromString("f91ab496-9d45-41c6-bab1-98ae45178b21"))
                    .title("The Withcer")
                    .releaseDate(LocalDate.of(2024, 12, 12))
                    .genre(Genre.FANTASY)
                    .author(Sapkowski)
                    .user(Michal)
                    .build();

            Book AtomoweNawyki = Book.builder()
                    .id(UUID.fromString("0047c7c6-e838-4fac-b106-e15396765860"))
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

//            bookRepository.create(LOTR);
//            bookRepository.create(Metro);
//            bookRepository.create(Wiedzmin);
//            bookRepository.create(AtomoweNawyki);
        }

    }
}
