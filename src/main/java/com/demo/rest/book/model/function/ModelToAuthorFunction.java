package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToAuthorFunction implements Function<AuthorCreateModel, Author>, Serializable {
    @Override
    public Author apply(AuthorCreateModel model) {
        return Author.builder()
                .id(model.getId())
                .name(model.getName())
                .nationality(model.getNationality())
                .build();
    }
}
