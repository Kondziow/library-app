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
public class PutBookRequest {
    private String title;
    private LocalDate releaseDate;
    private Genre genre;

    private UUID author;
    private UUID user;
}
