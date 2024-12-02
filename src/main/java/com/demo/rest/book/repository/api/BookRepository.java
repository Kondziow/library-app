package com.demo.rest.book.repository.api;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.repository.api.Repository;
import com.demo.rest.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends Repository<Book, UUID> {
    Optional<Book> findByIdAndUser(UUID id, User user);
    List<Book> findAllByAuthor(Author author);
    List<Book> findAllByUser(User user);
    List<Book> findByAuthorAndUser(User user, Author author);
}
