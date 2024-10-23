package com.demo.rest.book.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthorModel {
    private UUID id;
    private String name;
    private String nationality;
}
