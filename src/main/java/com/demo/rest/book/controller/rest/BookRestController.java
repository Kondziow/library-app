package com.demo.rest.book.controller.rest;

import com.demo.rest.book.controller.api.AuthorController;
import com.demo.rest.book.controller.api.BookController;
import com.demo.rest.book.dto.GetBookResponse;
import com.demo.rest.book.dto.GetBooksResponse;
import com.demo.rest.book.dto.PatchBookRequest;
import com.demo.rest.book.dto.PutBookRequest;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.DtoFunctionFactory;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.UUID;

@Path("")
public class BookRestController implements BookController {
    private final BookService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public BookRestController(BookService service,
                              DtoFunctionFactory factory,
                              @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetBooksResponse getBooks() {
        return factory.booksToResponse().apply(service.findAll());
    }

    @Override
    public GetBookResponse getBook(UUID id) {
        return service.find(id)
                .map(factory.bookToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetBooksResponse getAuthorBook(UUID id) {
        return service.findAllByAuthor(id)
                .map(factory.booksToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetBooksResponse getUserBook(UUID id) {
        return service.findAllByUser(id)
                .map(factory.booksToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putBook(UUID id, PutBookRequest request) {
        try {
            service.create(factory.requestToBook().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(AuthorController.class, "getBook")
                    .build(id)
                    .toString());

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateBook(UUID id, PatchBookRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateBook().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteBook(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
