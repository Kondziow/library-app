package com.demo.rest.book.controller.simple;

import com.demo.rest.book.controller.api.AuthorController;
import com.demo.rest.book.dto.GetAuthorResponse;
import com.demo.rest.book.dto.GetAuthorsResponse;
import com.demo.rest.book.dto.PatchAuthorRequest;
import com.demo.rest.book.dto.PutAuthorRequest;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.controller.servlet.exception.BadRequestException;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class AuthorSimpleController implements AuthorController {
    private final AuthorService service;
    private final DtoFunctionFactory factory;

    @Inject
    public AuthorSimpleController(AuthorService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetAuthorsResponse getAuthors() {
        return factory.authorsToResponse().apply(service.findAll());
    }

    @Override
    public GetAuthorResponse getAuthor(UUID id) {
        return service.find(id)
                .map(factory.authorToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putAuthor(UUID id, PutAuthorRequest request) {
        try {
            service.create(factory.requestToAuthor().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateAuthor(UUID id, PatchAuthorRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateAuthor().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteAuthor(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
