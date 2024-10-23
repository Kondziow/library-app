package com.demo.rest.book.model;

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
public class BookCreateModel {
    private UUID id;
    private String title;
    private LocalDate releaseDate;
    private Genre genre;
    private AuthorModel author;
}
