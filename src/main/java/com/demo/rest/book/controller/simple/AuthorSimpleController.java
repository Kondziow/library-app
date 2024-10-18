package com.demo.rest.book.controller.simple;

import com.demo.rest.book.controller.api.AuthorController;
import com.demo.rest.book.dto.GetAuthorResponse;
import com.demo.rest.book.dto.GetAuthorsResponse;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.controller.servlet.exception.NotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class AuthorSimpleController implements AuthorController {
    private final AuthorService authorService;
    private final DtoFunctionFactory factory;

    @Inject
    public AuthorSimpleController(AuthorService authorService, DtoFunctionFactory factory) {
        this.authorService = authorService;
        this.factory = factory;
    }

    @Override
    public GetAuthorsResponse getAuthors() {
        return factory.authorsToResponse().apply(authorService.findAll());
    }

    @Override
    public GetAuthorResponse getAuthor(UUID id) {
        return authorService.findById(id)
                .map(factory.authorToResponse())
                .orElseThrow(NotFoundException::new);
    }
}
