package com.demo.rest.book.repository.api;

import com.demo.rest.book.entity.Book;
import com.demo.rest.repository.api.Repository;

import java.util.UUID;

public interface BookRepository extends Repository<Book, UUID> {
}
