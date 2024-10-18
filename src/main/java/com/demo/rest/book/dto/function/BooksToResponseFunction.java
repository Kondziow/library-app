package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.GetBooksResponse;
import com.demo.rest.book.entity.Book;

import java.util.List;
import java.util.function.Function;

public class BooksToResponseFunction implements Function<List<Book>, GetBooksResponse> {
    @Override
    public GetBooksResponse apply(List<Book> entities) {
        return GetBooksResponse.builder()
                .books(entities.stream()
                        .map(book -> GetBooksResponse.Book.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .releaseDate(book.getReleaseDate())
                                .genre(book.getGenre())
                                .build())
                        .toList())
                .build();
    }
}
