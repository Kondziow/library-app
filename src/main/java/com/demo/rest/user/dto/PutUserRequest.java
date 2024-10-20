package com.demo.rest.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PutUserRequest {
    private String username;
    private String emailAddress;
}
