package com.demo.rest.book.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetAuthorResponse {
    private UUID id;
    private String name;
    private String nationality;
}
