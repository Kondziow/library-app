package com.demo.rest.book.repository.api;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.repository.api.Repository;
import com.demo.rest.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends Repository<Book, UUID> {
    List<Book> findAllByAuthor(Author author);
    List<Book> findAllByUser(User user);
}
