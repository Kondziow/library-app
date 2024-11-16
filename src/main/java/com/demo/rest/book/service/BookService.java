package com.demo.rest.book.service;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.repository.api.AuthorRepository;
import com.demo.rest.book.repository.api.BookRepository;
import com.demo.rest.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Inject
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    public Optional<Book> find(UUID id) {
        return bookRepository.find(id);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void create(Book book, UUID authorId) {
        if (bookRepository.find(book.getId()).isPresent()) {
            throw new IllegalArgumentException("Character already exists.");
        }
        if (authorRepository.find(book.getAuthor().getId()).isEmpty()) {
            throw new IllegalArgumentException("Profession does not exists.");
        }
        bookRepository.create(book);

//        authorRepository.find(authorId).ifPresentOrElse(
//                author -> {
//                    book.setAuthor(author);
//                    bookRepository.create(book);
//                },
//                () -> {
//                    throw new NotFoundException("Author not found");
//                }
//        );
    }

    @Transactional
    public void update(Book book) {
        bookRepository.update(book);
    }

    @Transactional
    public void delete(UUID id) {
        bookRepository.delete(bookRepository.find(id).orElseThrow());
    }

    public Optional<List<Book>> findAllByAuthor(UUID id) {
        return authorRepository.find(id)
                .map(bookRepository::findAllByAuthor);
    }

    public Optional<List<Book>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(bookRepository::findAllByUser);
    }
}
