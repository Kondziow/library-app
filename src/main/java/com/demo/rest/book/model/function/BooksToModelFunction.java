package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.BooksModel;

import java.util.List;
import java.util.function.Function;

public class BooksToModelFunction implements Function<List<Book>, BooksModel> {
    @Override
    public BooksModel apply(List<Book> entity) {
        return BooksModel.builder()
                .books(entity.stream()
                        .map(book -> BooksModel.Book.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .build())
                        .toList())
                .build();
    }
}
