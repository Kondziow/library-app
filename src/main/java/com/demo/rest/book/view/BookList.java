package com.demo.rest.book.view;

import com.demo.rest.book.model.BooksModel;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class BookList {
    private final BookService service;
    private final ModelFunctionFactory factory;

    private BooksModel books;

    @Inject
    public BookList(BookService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public BooksModel getBooks() {
        if (books == null) {
            books = factory.booksToModel().apply(service.findAll());
        }
        return books;
    }

    public String deleteAction(BooksModel.Book book) {
        service.delete(book.getId());
        return "book_list?faces-redirect=true";
    }
}
