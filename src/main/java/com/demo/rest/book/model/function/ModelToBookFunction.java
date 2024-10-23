package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.BookCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToBookFunction implements Function<BookCreateModel, Book>, Serializable {
    @Override
    public Book apply(BookCreateModel model) {
        return Book.builder()
                .id(model.getId())
                .releaseDate(model.getReleaseDate())
                .genre(model.getGenre())
                .author(Author.builder()
                        .id(model.getAuthor().getId())
                        .build())
                .build();
    }
}
