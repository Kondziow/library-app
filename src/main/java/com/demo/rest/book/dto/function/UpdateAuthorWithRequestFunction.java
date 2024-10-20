package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.PatchAuthorRequest;
import com.demo.rest.book.entity.Author;

import java.util.function.BiFunction;

public class UpdateAuthorWithRequestFunction implements BiFunction<Author, PatchAuthorRequest, Author> {
    @Override
    public Author apply(Author entity, PatchAuthorRequest request) {
        return Author.builder()
                .id(entity.getId())
                .name(request.getName())
                .nationality(request.getNationality())
                .build();
    }
}
