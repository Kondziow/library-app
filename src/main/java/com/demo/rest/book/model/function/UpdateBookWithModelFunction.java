package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.BookEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateBookWithModelFunction implements BiFunction<Book, BookEditModel, Book>, Serializable {
    @Override
    public Book apply(Book entity, BookEditModel request) {
        return Book.builder()
                .id(entity.getId())
                .title(request.getTitle())
                .releaseDate(request.getReleaseDate())
                .genre(request.getGenre())
                .author(entity.getAuthor())
                .build();
    }
}
