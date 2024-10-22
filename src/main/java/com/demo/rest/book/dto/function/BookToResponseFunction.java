package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.GetBookResponse;
import com.demo.rest.book.entity.Book;

import java.util.function.Function;

public class BookToResponseFunction implements Function<Book, GetBookResponse> {
    @Override
    public GetBookResponse apply(Book book) {
        return GetBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .releaseDate(book.getReleaseDate())
                .genre(book.getGenre())
                .author(GetBookResponse.Author.builder()
                        .id(book.getAuthor().getId())
                        .name(book.getAuthor().getName())
                        .build())
                .build();
    }
}
