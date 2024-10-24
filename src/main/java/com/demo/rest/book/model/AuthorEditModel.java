package com.demo.rest.book.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthorEditModel {
    private String name;
    private String nationality;
}
