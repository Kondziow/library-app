package com.demo.rest.book.model;

import com.demo.rest.book.entity.Genre;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BookEditModel {
    private String title;
    private LocalDate releaseDate;
    private Genre genre;
}
