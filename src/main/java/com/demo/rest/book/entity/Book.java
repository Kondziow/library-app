package com.demo.rest.book.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Book {
    private UUID id;
    private String title;
    private LocalDate releaseDate;
    private Author author;
    private Genre genre;
}
