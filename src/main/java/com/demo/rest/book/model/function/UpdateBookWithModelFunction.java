package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.BookEditModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.BiFunction;

public class UpdateBookWithModelFunction implements BiFunction<Book, BookEditModel, Book>, Serializable {
    public static LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Nieprawidłowy format daty: " + dateString);
            return null;
        }
    }

    @Override
    public Book apply(Book entity, BookEditModel request) {
        return Book.builder()
                .id(entity.getId())
                .title(request.getTitle())
                .releaseDate(convertStringToLocalDate(request.getReleaseDate()))
                .genre(request.getGenre())
                .author(Author.builder()
                        .id(entity.getAuthor().getId())
                        .build())
                .build();
    }
}
