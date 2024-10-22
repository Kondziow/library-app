package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.PatchBookRequest;
import com.demo.rest.book.entity.Book;

import java.util.function.BiFunction;

public class UpdateBookWithRequestFunction implements BiFunction<Book, PatchBookRequest, Book> {
    @Override
    public Book apply(Book entity, PatchBookRequest request) {
        return Book.builder()
                .id(entity.getId())
                .title(request.getTitle())
                .releaseDate(entity.getReleaseDate())
                .genre(request.getGenre())
                .author(entity.getAuthor())
                .user(entity.getUser())
                .build();
    }
}
