package com.demo.rest.book.model.function;

import com.demo.rest.book.entity.Book;
import com.demo.rest.book.model.AuthorModel;
import com.demo.rest.book.model.BookEditModel;
import com.demo.rest.user.model.function.UserToModelFunction;

import java.io.Serializable;
import java.util.function.Function;

public class BookToEditModelFunction implements Function<Book, BookEditModel>, Serializable {

    private final UserToModelFunction userToModelFunction;

    public BookToEditModelFunction(UserToModelFunction userToModelFunction) {
        this.userToModelFunction = userToModelFunction;
    }

    @Override
    public BookEditModel apply(Book entity) {
        return BookEditModel.builder()
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseDate().toString())
                .genre(entity.getGenre())
                .author(AuthorModel.builder()
                        .id(entity.getAuthor().getId())
                        .name(entity.getAuthor().getName())
                        .build())
                .user(userToModelFunction.apply(entity.getUser()))
                .version(entity.getVersion())
                .build();
    }
}
