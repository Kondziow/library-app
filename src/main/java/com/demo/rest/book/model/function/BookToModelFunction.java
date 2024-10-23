package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.BookModel;

import java.io.Serializable;
import java.util.function.Function;

public class BookToModelFunction implements Function<Book, BookModel>, Serializable {
    @Override
    public BookModel apply(Book entity) {
        return BookModel.builder()
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseDate())
                .genre(entity.getGenre())
                .author(entity.getAuthor().getName())
                .build();
    }
}
