package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.GetAuthorResponse;
import com.demo.rest.book.entity.Author;

import java.util.function.Function;

public class AuthorToResponseFunction implements Function<Author, GetAuthorResponse> {
    @Override
    public GetAuthorResponse apply(Author author) {
        return GetAuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .nationality(author.getNationality())
                .build();
    }
}
