package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.BookEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class BookToEditModelFunction implements Function<Book, BookEditModel>, Serializable {
    @Override
    public BookEditModel apply(Book entity) {
        return BookEditModel.builder()
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseDate())
                .genre(entity.getGenre())
                .build();
    }
}
