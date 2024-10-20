package com.demo.rest.component;

import com.demo.rest.book.dto.function.*;
import com.demo.rest.user.dto.function.RequestToUserFunction;
import com.demo.rest.user.dto.function.UpdateUserWithRequestFunction;
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
    public RequestToAuthorFunction requestToAuthor() {return new RequestToAuthorFunction();}
    public UpdateAuthorWithRequestFunction updateAuthor() {return new UpdateAuthorWithRequestFunction();}

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
    public RequestToUserFunction requestToUser() {return new RequestToUserFunction();}
    public UpdateUserWithRequestFunction updateUser() {return new UpdateUserWithRequestFunction();}
}
