package com.demo.rest.book.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PutAuthorRequest {
    private String name;
    private String nationality;
}
