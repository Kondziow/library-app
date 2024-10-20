package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.PutAuthorRequest;
import com.demo.rest.book.entity.Author;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToAuthorFunction implements BiFunction<UUID, PutAuthorRequest, Author> {
    @Override
    public Author apply(UUID id, PutAuthorRequest request) {
        return Author.builder()
                .id(id)
                .name(request.getName())
                .nationality(request.getNationality())
                .build();
    }
}
