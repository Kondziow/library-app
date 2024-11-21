package com.demo.rest.component;

import com.demo.rest.book.dto.function.*;
import com.demo.rest.user.dto.function.*;
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
    public RequestToBookFunction requestToBook() {return new RequestToBookFunction();}
    public UpdateBookWithRequestFunction updateBook() {return new UpdateBookWithRequestFunction();}

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }
    public RequestToUserFunction requestToUser() {return new RequestToUserFunction();}
    public UpdateUserWithRequestFunction updateUser() {return new UpdateUserWithRequestFunction();}
    public UpdateUserPasswordWithRequestFunction updatePassword() {return new UpdateUserPasswordWithRequestFunction();}
}
