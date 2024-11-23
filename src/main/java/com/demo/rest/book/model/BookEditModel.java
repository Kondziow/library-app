package com.demo.rest.book.model;

import com.demo.rest.book.entity.Author;
import com.demo.rest.book.entity.Genre;
import com.demo.rest.user.model.UserModel;
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
    private String releaseDate;
    private Genre genre;
    private AuthorModel author;
    private UserModel user;
}
