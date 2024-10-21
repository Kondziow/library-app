package com.demo.rest.book.controller.simple;

import com.demo.rest.book.controller.api.BookController;
import com.demo.rest.book.dto.GetBookResponse;
import com.demo.rest.book.dto.GetBooksResponse;
import com.demo.rest.book.dto.PatchBookRequest;
import com.demo.rest.book.dto.PutBookRequest;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.controller.servlet.exception.BadRequestException;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class BookSimpleController implements BookController {
    private final BookService service;
    private final DtoFunctionFactory factory;

    @Inject
    public BookSimpleController(BookService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetBooksResponse getBooks() {
        return factory.booksToResponse().apply(service.findAll());
    }

    @Override
    public GetBookResponse getBook(UUID id) {
        return service.findById(id)
                .map(factory.bookToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putBook(UUID id, PutBookRequest request) {
        try {
            service.create(factory.requestToBook().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateBook(UUID id, PatchBookRequest request) {
        service.findById(id).ifPresentOrElse(
                entity -> service.update(factory.updateBook().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteBook(UUID id) {
        service.findById(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
