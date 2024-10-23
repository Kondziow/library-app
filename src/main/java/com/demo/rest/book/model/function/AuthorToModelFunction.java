package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorModel;

import java.io.Serializable;
import java.util.function.Function;

public class AuthorToModelFunction implements Function<Author, AuthorModel>, Serializable {
    @Override
    public AuthorModel apply(Author entity) {
        return AuthorModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nationality(entity.getNationality())
                .build();
    }
}
