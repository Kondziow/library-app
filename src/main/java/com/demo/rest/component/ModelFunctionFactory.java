package com.demo.rest.component;

import com.demo.rest.book.model.function.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModelFunctionFactory {
    public AuthorToModelFunction authorToModel() {return new AuthorToModelFunction();}
    public AuthorsToModelFunction authorsToModel() {return new AuthorsToModelFunction();}
    public ModelToAuthorFunction modelToAuthor() {return new ModelToAuthorFunction();}
    public AuthorToEditModelFunction authorToEditModel() {return new AuthorToEditModelFunction();}
    public UpdateAuthorWithModelFunction updateAuthor() {return new UpdateAuthorWithModelFunction();}
    public BookToModelFunction bookToModel() {return new BookToModelFunction();}
    public BooksToModelFunction booksToModel() {return new BooksToModelFunction();}
    public ModelToBookFunction modelToBook() {return new ModelToBookFunction();}
    public BookToEditModelFunction bookToEditModel() {return new BookToEditModelFunction();}
    public UpdateBookWithModelFunction updateBook() {return new UpdateBookWithModelFunction();}
}
