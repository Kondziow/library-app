package com.demo.rest.book.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PatchAuthorRequest {
    private String name;
    private String nationality;
}
