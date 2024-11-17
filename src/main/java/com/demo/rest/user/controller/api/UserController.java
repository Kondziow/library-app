package com.demo.rest.user.controller.api;

import com.demo.rest.user.dto.GetUserResponse;
import com.demo.rest.user.dto.GetUsersResponse;
import com.demo.rest.user.dto.PatchUserRequest;
import com.demo.rest.user.dto.PutUserRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface UserController {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putUser(@PathParam("id") UUID id, PutUserRequest request);

    @PATCH
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void updateUser(@PathParam("id") UUID id, PatchUserRequest request);

    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteUser(@PathParam("id") UUID id);
}
