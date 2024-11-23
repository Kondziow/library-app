package com.demo.rest.book.view;

import com.demo.rest.book.model.BooksModel;
import com.demo.rest.book.service.AuthorService;
import com.demo.rest.book.service.BookService;
import com.demo.rest.component.ModelFunctionFactory;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@ViewScoped
@Named
public class BookList implements Serializable {
    private BookService service;
    private final ModelFunctionFactory factory;

    private BooksModel books;

    @Inject
    public BookList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    private void setBookService(BookService service) {
        this.service = service;
    }

    public BooksModel getBooks() {
        if (books == null) {
            books = factory.booksToModel().apply(service.findAllForCallerPrincipal());
        }
        return books;
    }

    public String deleteAction(BooksModel.Book book) {
        service.delete(book.getId());
        books = null;
        return "book_list?faces-redirect=true";
    }

    public void cancelDeleteAction(BooksModel.Book book) {
        service.cancelDelete(book.getId());
        books = null;
    }
}
