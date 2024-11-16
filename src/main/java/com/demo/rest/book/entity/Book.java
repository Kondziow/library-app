package com.demo.rest.book.entity;

import com.demo.rest.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    private UUID id;
    private String title;
    private LocalDate releaseDate;
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;
}
