package com.demo.rest.book.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Author implements Serializable {
    private UUID id;
    private String name;
    private String nationality;
    @Singular
    private List<Book> books;
}
