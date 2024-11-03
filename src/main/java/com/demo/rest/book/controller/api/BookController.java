package com.demo.rest.book.controller.api;

import com.demo.rest.book.dto.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface BookController {

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    GetBooksResponse getBooks();

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetBookResponse getBook(@PathParam("id") UUID id);

    @GET
    @Path("/authors/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    GetBooksResponse getAuthorBook(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    GetBooksResponse getUserBook(@PathParam("id") UUID id);

    @PUT
    @Path("/authors/{authorId}/books/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    void putBook(@PathParam("authorId") UUID authorId, @PathParam("bookId") UUID bookId, PutBookRequest request);

    @PATCH
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void updateBook(@PathParam("id") UUID id, PatchBookRequest request);

    @DELETE
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteBook(@PathParam("id") UUID id);
}
