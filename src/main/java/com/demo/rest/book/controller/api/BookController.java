package com.demo.rest.book.controller.api;

import com.demo.rest.book.dto.*;

import java.util.UUID;

public interface BookController {
    GetBooksResponse getBooks();
    GetBookResponse getBook(UUID id);
    GetBooksResponse getAuthorBook(UUID id);
    void putBook(UUID id, PutBookRequest request);
    void updateBook(UUID id, PatchBookRequest request);
    void deleteBook(UUID id);
}
