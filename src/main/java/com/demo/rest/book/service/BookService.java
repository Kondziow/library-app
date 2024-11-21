package com.demo.rest.book.service;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.repository.api.AuthorRepository;
import com.demo.rest.book.repository.api.BookRepository;
import com.demo.rest.user.repository.api.UserRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
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

    public void create(Book book, UUID authorId) {
        System.out.println("W create");
        if (bookRepository.find(book.getId()).isPresent()) {
            System.out.println("1");
            throw new IllegalArgumentException("Character already exists.");
        }
        Optional<Author> author = authorRepository.find(authorId);
        if (author.isEmpty()) {
            System.out.println("2");
            throw new IllegalArgumentException("Profession does not exists.");
        }
        System.out.println("book");
        System.out.println(book);
        book.setAuthor(author.get());
        System.out.println(book);
        bookRepository.create(book);
    }

    public void update(Book book) {
        bookRepository.update(book);
    }

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
