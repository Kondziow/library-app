package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.model.AuthorEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateAuthorWithModelFunction implements BiFunction<Author, AuthorEditModel, Author>, Serializable {
    @Override
    public Author apply(Author entity, AuthorEditModel model) {
        return Author.builder()
                .id(entity.getId())
                .name(model.getName())
                .nationality(model.getNationality())
                .build();
    }
}
