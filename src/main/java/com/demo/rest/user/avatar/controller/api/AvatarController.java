package com.demo.rest.user.avatar.controller.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface AvatarController {

    @GET
    @Path("/users/{id}/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    byte[] getAvatar(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    void putAvatar(@PathParam("id") UUID id, InputStream avatar);

    @GET
    @Path("/users/{id}/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteAvatar(@PathParam("id") UUID id);
}
