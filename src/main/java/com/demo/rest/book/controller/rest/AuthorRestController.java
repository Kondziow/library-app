package com.demo.rest.book.controller.rest;

import com.demo.rest.book.controller.api.AuthorController;
import com.demo.rest.book.dto.GetAuthorResponse;
import com.demo.rest.book.dto.GetAuthorsResponse;
import com.demo.rest.book.dto.PatchAuthorRequest;
import com.demo.rest.book.dto.PutAuthorRequest;
import com.demo.rest.book.service.AuthorService;
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
public class AuthorRestController implements AuthorController {
    private final AuthorService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public AuthorRestController(AuthorService service,
                                DtoFunctionFactory factory,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
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

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(AuthorController.class, "getAuthor")
                    .build(id)
                    .toString());

            throw new WebApplicationException(Response.Status.CREATED);
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
