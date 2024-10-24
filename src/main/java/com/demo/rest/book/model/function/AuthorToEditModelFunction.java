package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class AuthorToEditModelFunction implements Function<Author, AuthorEditModel>, Serializable {
    @Override
    public AuthorEditModel apply(Author entity) {
        return AuthorEditModel.builder()
                .name(entity.getName())
                .nationality(entity.getNationality())
                .build();
    }
}
