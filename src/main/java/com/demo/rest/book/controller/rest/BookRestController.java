package com.demo.rest.book.controller.rest;

import com.demo.rest.authorization.exception.NoPrincipalException;
import com.demo.rest.authorization.exception.NoRolesException;
import com.demo.rest.book.controller.api.BookController;
import com.demo.rest.book.dto.GetBookResponse;
import com.demo.rest.book.dto.GetBooksResponse;
import com.demo.rest.book.dto.PatchBookRequest;
import com.demo.rest.book.dto.PutBookRequest;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.DtoFunctionFactory;
import com.demo.rest.user.entity.UserRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(UserRoles.USER)
public class BookRestController implements BookController {
    private BookService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public BookRestController(DtoFunctionFactory factory,
                              @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(BookService service) {
        this.service = service;
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
    public void putBook(UUID authorId, UUID bookId, PutBookRequest request) {
        try {
            service.createForCallerPrincipal(factory.requestToBook().apply(bookId, request), authorId);

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        } catch (NotFoundException ex) {
            throw new NotFoundException("Author not found");
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void updateBook(UUID id, PatchBookRequest request) {
        try {
            service.find(id).ifPresentOrElse(
                    entity -> {
                        try {
                            service.update(factory.updateBook().apply(entity, request));
                        } catch (EJBAccessException ex) {
                            log.log(Level.WARNING, ex.getMessage(), ex);
                            throw new ForbiddenException(ex.getMessage());
                        }
                    },
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (NoRolesException ex) {
            throw new ForbiddenException();
        } catch (NoPrincipalException ex) {
            throw new NotAuthorizedException("");
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                throw new BadRequestException(ex.getCause());
            }
        }
    }

    @Override
    public void deleteBook(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        service.delete(id);
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
