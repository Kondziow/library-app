package com.demo.rest.book.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Book implements Serializable {
    private UUID id;
    private String title;
    private LocalDate releaseDate;
    private Author author;
    private Genre genre;
}
