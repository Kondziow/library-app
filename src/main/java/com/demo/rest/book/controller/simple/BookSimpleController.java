package com.demo.rest.book.controller.simple;

import com.demo.rest.book.controller.api.BookController;
import com.demo.rest.book.dto.GetBookResponse;
import com.demo.rest.book.dto.GetBooksResponse;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class BookSimpleController implements BookController {
    private final BookService bookService;
    private final DtoFunctionFactory factory;

    @Inject
    public BookSimpleController(BookService bookService, DtoFunctionFactory factory) {
        this.bookService = bookService;
        this.factory = factory;
    }

    @Override
    public GetBooksResponse getBooks() {
        return factory.booksToResponse().apply(bookService.findAll());
    }

    @Override
    public GetBookResponse getBook(UUID id) {
        return bookService.findById(id)
                .map(factory.bookToResponse())
                .orElseThrow(NotFoundException::new);
    }
}
