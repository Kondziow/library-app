package com.demo.rest.user.entity;

import com.demo.rest.book.entity.Book;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private UUID id;
    private String username;
    private String emailAddress;
    private List<Book> books;
}
