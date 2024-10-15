package com.demo.rest.book.entity;

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
public class Author {
    private UUID id;
    private String name;
    private String nationality;
    private List<Book> books;
}
