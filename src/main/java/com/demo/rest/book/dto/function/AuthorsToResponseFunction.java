package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.GetAuthorsResponse;
import com.demo.rest.book.entity.Author;

import java.util.List;
import java.util.function.Function;

public class AuthorsToResponseFunction implements Function<List<Author>, GetAuthorsResponse> {

    @Override
    public GetAuthorsResponse apply(List<Author> entities) {
        return GetAuthorsResponse.builder()
                .authors(entities.stream()
                        .map(author -> GetAuthorsResponse.Author.builder()
                                .id(author.getId())
                                .name(author.getName())
                                .nationality(author.getNationality())
                                .build())
                        .toList())
                .build();
    }
}
