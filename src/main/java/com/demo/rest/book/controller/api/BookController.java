package com.demo.rest.book.controller.api;

import com.demo.rest.book.dto.GetBookResponse;
import com.demo.rest.book.dto.GetBooksResponse;

import java.util.UUID;

public interface BookController {
    GetBooksResponse getBooks();
    GetBookResponse getBook(UUID id);
}
