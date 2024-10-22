package com.demo.rest.book.dto.function;

import com.demo.rest.book.dto.PutBookRequest;
import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToBookFunction implements BiFunction<UUID, PutBookRequest, Book> {
    @Override
    public Book apply(UUID id, PutBookRequest request) {
        return Book.builder()
                .id(id)
                .title(request.getTitle())
                .releaseDate(request.getReleaseDate())
                .genre(request.getGenre())
                .author(Author.builder().id(request.getAuthor()).build())
                .user(User.builder().id(request.getUser()).build())
                .build();
    }
}
