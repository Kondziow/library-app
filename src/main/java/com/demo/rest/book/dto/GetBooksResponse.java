package com.demo.rest.book.dto;

import com.demo.rest.book.entity.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetBooksResponse {
    @Singular
    private List<Book> books;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Book {
        private UUID id;
        private String title;
        private LocalDate releaseDate;
        private Genre genre;
    }
}
