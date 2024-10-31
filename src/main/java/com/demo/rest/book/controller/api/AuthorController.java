package com.demo.rest.book.controller.api;

import com.demo.rest.book.dto.GetAuthorResponse;
import com.demo.rest.book.dto.GetAuthorsResponse;
import com.demo.rest.book.dto.PatchAuthorRequest;
import com.demo.rest.book.dto.PutAuthorRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface AuthorController {

    @GET
    @Path("/authors")
    @Produces(MediaType.APPLICATION_JSON)
    GetAuthorsResponse getAuthors();

    @GET
    @Path("/authors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetAuthorResponse getAuthor(@PathParam("id") UUID id);

    @PUT
    @Path("/authors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putAuthor(@PathParam("id") UUID id, PutAuthorRequest request);

    @PATCH
    @Path("/authors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void updateAuthor(@PathParam("id") UUID id, PatchAuthorRequest request);

    @DELETE
    @Path("/authors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteAuthor(@PathParam("id") UUID id);
}
