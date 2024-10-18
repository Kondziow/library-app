package com.demo.rest.component;

import com.demo.rest.book.dto.function.AuthorToResponseFunction;
import com.demo.rest.book.dto.function.AuthorsToResponseFunction;
import com.demo.rest.book.dto.function.BookToResponseFunction;
import com.demo.rest.book.dto.function.BooksToResponseFunction;
import com.demo.rest.user.dto.function.UserToResponseFunction;
import com.demo.rest.user.dto.function.UsersToResponseFunction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DtoFunctionFactory {
    public AuthorsToResponseFunction authorsToResponse() {
        return new AuthorsToResponseFunction();
    }
    public AuthorToResponseFunction authorToResponse() {
        return new AuthorToResponseFunction();
    }

    public BooksToResponseFunction booksToResponse() {
        return new BooksToResponseFunction();
    }
    public BookToResponseFunction bookToResponse() {
        return new BookToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }
}
