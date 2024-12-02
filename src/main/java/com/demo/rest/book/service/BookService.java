package com.demo.rest.book.service;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.repository.api.AuthorRepository;
import com.demo.rest.book.repository.api.BookRepository;
import com.demo.rest.user.entity.User;
import com.demo.rest.user.entity.UserRoles;
import com.demo.rest.user.repository.api.UserRepository;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final SecurityContext securityContext;
    private TimerService timerService;
    private SessionContext context;

    @Resource
    public void setTimerService(TimerService timerService) {
        this.timerService = timerService;
    }

    @Resource
    public void setContext(SessionContext context) {
        this.context = context;
    }

    @Inject
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, UserRepository userRepository, SecurityContext securityContext) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Book> find(UUID id) {
        Optional<Book> book = bookRepository.find(id);
        checkAdminRoleOrOwner(book);
        return book;
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Book> find(User user, UUID id) {
        return bookRepository.findByIdAndUser(id, user);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Book> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Book> findAll() {
        return findAllForCallerPrincipal();
//        return bookRepository.findAll();
    }

    @RolesAllowed(UserRoles.USER)
    public List<Book> findAll(User user) {
        return bookRepository.findAllByUser(user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Book> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return bookRepository.findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(user);
    }

    //    @RolesAllowed(UserRoles.USER)
//    @PermitAll
    @RolesAllowed(UserRoles.ADMIN)
    public void create(Book book, UUID authorId) {
        if (bookRepository.find(book.getId()).isPresent()) {
            throw new IllegalArgumentException("Book already exists.");
        }
        Optional<Author> author = authorRepository.find(authorId);
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author does not exists.");
        }
        if (userRepository.find(book.getUser().getId()).isEmpty()) {
            throw new IllegalArgumentException("User does not exists.");
        }
        book.setAuthor(author.get());
        bookRepository.create(book);

        log.info(String.format("User: %s CREATE Book ID: %s",
                securityContext.getCallerPrincipal().getName(), book.getId()));
    }

    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Book book, UUID authorId) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        book.setUser(user);
        create(book, authorId);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(Book book) {
        checkAdminRoleOrOwner(bookRepository.find(book.getId()));
        bookRepository.update(book);

        log.info(String.format("User: %s UPDATE Book ID: %s",
                securityContext.getCallerPrincipal().getName(), book.getId()));
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(bookRepository.find(id));
        bookRepository.delete(bookRepository.find(id).orElseThrow());

        log.info(String.format("User: %s DELETE Book ID: %s",
                securityContext.getCallerPrincipal().getName(), id));
    }

    @RolesAllowed(UserRoles.USER)
    public List<Book> findByAuthorForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findAllByAuthor(id).get();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        Author author = Author.builder().id(id).build();
        return findAllByAuthorAndUser(user, author);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<List<Book>> findAllByAuthor(UUID id) {
        return authorRepository.find(id)
                .map(bookRepository::findAllByAuthor);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Book> findAllByAuthorAndUser(User user, Author author) {
        return bookRepository.findByAuthorAndUser(user, author);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<List<Book>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(bookRepository::findAllByUser);
    }

    @RolesAllowed(UserRoles.USER)
    public void cancelDelete(UUID id) {
        checkAdminRoleOrOwner(bookRepository.find(id));
        timerService.getTimers().stream()
                .filter(timer -> timer.getInfo().equals(id))
                .forEach(Timer::cancel);
    }

    private void checkAdminRoleOrOwner(Optional<Book> book) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && book.isPresent()
                && book.get().getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
