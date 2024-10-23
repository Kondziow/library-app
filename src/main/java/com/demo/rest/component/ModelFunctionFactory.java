package com.demo.rest.component;

import com.demo.rest.book.model.function.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModelFunctionFactory {
    public AuthorToModelFunction authorToModel() {return new AuthorToModelFunction();}
    public BooksToModelFunction booksToModel() {return new BooksToModelFunction();}
    public BookToEditModelFunction bookToEditModel() {return new BookToEditModelFunction();}
    public BookToModelFunction bookToModel() {return new BookToModelFunction();}
    public ModelToBookFunction modelToBook() {return new ModelToBookFunction();}
    public UpdateBookWithModelFunction updateBook() {return new UpdateBookWithModelFunction();}
}
