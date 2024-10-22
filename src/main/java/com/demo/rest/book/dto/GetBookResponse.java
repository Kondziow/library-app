package com.demo.rest.book.dto;

import com.demo.rest.book.entity.Genre;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetBookResponse {
    private UUID id;
    private String title;
    private LocalDate releaseDate;
    private Genre genre;

    private Author author;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Author {
        private UUID id;
        private String name;
    }
}
