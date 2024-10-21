package com.demo.rest.book.dto;

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
public class PatchBookRequest {
    private String title;
    private LocalDate releaseDate;
    private Genre genre;
}
