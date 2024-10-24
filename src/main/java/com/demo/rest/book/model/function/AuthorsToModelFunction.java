package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorModel;
import com.demo.rest.book.model.AuthorsModel;

import java.util.List;
import java.util.function.Function;

public class AuthorsToModelFunction implements Function<List<Author>, AuthorsModel> {
    @Override
    public AuthorsModel apply(List<Author> entity) {
        return AuthorsModel.builder()
                .authors(entity.stream()
                        .map(author -> AuthorsModel.Author.builder()
                                .id(author.getId())
                                .name(author.getName())
                                .build())
                        .toList())
                .build();
    }
}
